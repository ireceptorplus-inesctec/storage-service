package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.VoteResultReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.HyperledgerFabricAPI;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.VoteType;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.DataTransformationRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.*;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.ToolsConfigProperties;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.TraceabilityDataMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DatasetService;
import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/traceabilityData")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class TraceabilityDataController
{
    HyperledgerFabricAPI blockchainAPI;

    FileSystemManager fileSystemManager;

    ToolsConfigProperties toolsConfigProperties;

    DatasetService datasetService;

    TraceabilityDataMapper traceabilityDataMapper;

    @Autowired
    public TraceabilityDataController(HyperledgerFabricAPI blockchainAPI, FileSystemManager fileSystemManager, ToolsConfigProperties toolsConfigProperties, DatasetService datasetService, TraceabilityDataMapper traceabilityDataMapper)
    {
        this.blockchainAPI = blockchainAPI;
        this.fileSystemManager = fileSystemManager;
        this.toolsConfigProperties = toolsConfigProperties;
        this.datasetService = datasetService;
        this.traceabilityDataMapper = traceabilityDataMapper;
    }

    @Operation(summary = "Creates a traceability data entry on the blockchain.")
    @PostMapping
    public TraceabilityDataReturnType createTraceabilityDataEntry(@Parameter(description = "The new instance of ProcessingStep to be registered on the blockchain as a tracability data entry") @RequestBody @Valid ProcessingStepDTO processingStepDTO) throws BlockchainAPIException
    {
        ArrayList<DownloadbleFile> inputDatasetFiles = new ArrayList<>(processingStepDTO.getInputDatasets().stream().map(
                dataset -> traceabilityDataMapper.datasetToDownloadableFile(dataset)).collect(Collectors.toList()));
        ArrayList<DownloadbleFile> outputDatasetFiles = new ArrayList<>(processingStepDTO.getOutputDatasets().stream().map(
                dataset -> traceabilityDataMapper.datasetToDownloadableFile(dataset)).collect(Collectors.toList()));
        Command command = traceabilityDataMapper.commandDTOToBlockchainCommand(processingStepDTO.getCommand());


        TraceabilityDataToBeSubmitted data = new TraceabilityDataToBeSubmitted(
                inputDatasetFiles, command, outputDatasetFiles);

        try
        {
            return blockchainAPI.createTraceabilityDataEntry(data);

        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    @Operation(summary = "Returns all traceability data entries awaiting validation on the blockchain.")
    @GetMapping()
    public List<TraceabilityDataReturnType> getAllTraceabilityDataAwaitingValidationFromBlokchain() throws BlockchainAPIException
    {
        try
        {
            return blockchainAPI.getTraceabilityDataAwaitingValidation();
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    @Operation(summary = "Runs a data processing pipeline corresponding to a traceability data entry. Returns weather the entry is valid or not.")
    @Parameter(name = "data", description = "The traceability data entry of which to run the processing")
    @PostMapping("run")
    public VoteResultReturnType runDataProcessingPipelineAndSubmitVote(TraceabilityDataReturnType data) throws ErrorComparingOutputs, BlockchainAPIException, TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand, UnsupportedTool
    {
            DataTransformationRunner runner = new DataTransformationRunner(data.getInputDatasets(),
                    data.getCommand(), data.getOutputDatasets(), DataTransformationRunner.RunningMode.VERIFY,
                    data.getCommand().getToolId(), data.getUuid(),fileSystemManager, toolsConfigProperties);

            boolean outputsMatch;
            try
            {
                runner.run();
                outputsMatch = runner.verifyIfOutputsMatch();
            } catch (ErrorComparingOutputs e)
            {
                String message = "Error verifying if outputs of processing match with expected outputs of traceablity data entry: ";
                iReceptorStorageServiceLogging.writeLogMessage(e, message);
                e.printStackTrace();
                throw new ErrorComparingOutputs(message);
            } catch (TryingToDownloadFileWithoutUrl e)
            {
                iReceptorStorageServiceLogging.writeLogMessage(e, e.getMessage());
                throw e;
            } catch (ErrorCopyingInputFiles e)
            {
                iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline awaiting validation on blockchain: Error copying input files.");
                throw e;
            } catch (ErrorRunningToolCommand e)
            {
                iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline awaiting validation on blockchain: Error running tool command.");
                throw e;
            } catch (UnsupportedTool e)
            {
                iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline awaiting validation on blockchain: Unsupported tool.");
                throw e;
            }

        VoteType voteType = outputsMatch ? VoteType.YES : VoteType.NO;
            return submitVoteToBlockchain(data.getUuid(), voteType);
    }

    @Operation(summary = "Submits a vote to the traceability data entry with the uuid received as parameter.")
    @Parameter(name = "dataUuid", description = "The uuid of the traceability data entry to vote for")
    @Parameter(name = "voteType", description = "A string representing the vote type: can be either \"YES\" or \"NO\"")
    @PostMapping("submit_vote")
    VoteResultReturnType submitVote(String dataUuid, String voteType) throws BlockchainAPIException
    {
        VoteType voteType_ = voteType.toUpperCase(Locale.ROOT).equals("YES") ? VoteType.YES : VoteType.NO;
        return submitVoteToBlockchain(dataUuid, voteType_);
    }

    private VoteResultReturnType submitVoteToBlockchain(String dataUuid, VoteType voteType_) throws BlockchainAPIException
    {
        try
        {
            return blockchainAPI.submitVote(dataUuid, voteType_);
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    private void handleBlockchainAPIException(BlockchainAPIException e) throws BlockchainAPIException
    {
        LogFactory.getLog(TraceabilityDataController.class).error(e.getMessage());
        StringWriter stackTrace = new StringWriter();
        PrintWriter stackTracePw = new PrintWriter(stackTrace);
        e.printStackTrace(stackTracePw);
        LogFactory.getLog(TraceabilityDataController.class).debug(stackTrace);
    }

}
