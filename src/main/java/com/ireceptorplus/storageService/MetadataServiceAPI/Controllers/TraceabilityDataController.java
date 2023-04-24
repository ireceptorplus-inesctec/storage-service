package com.ireceptorplus.storageService.MetadataServiceAPI.Controllers;

import com.ireceptorplus.storageService.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.storageService.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.storageService.BlockchainAPI.ChaincodeReturnDataTypes.VoteResultReturnType;
import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.storageService.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.storageService.BlockchainAPI.HyperledgerFabricAPI;
import com.ireceptorplus.storageService.BlockchainAPI.VoteType;
import com.ireceptorplus.storageService.DataTransformationRunning.DataTransformationRunner;
import com.ireceptorplus.storageService.DataTransformationRunning.Exceptions.*;
import com.ireceptorplus.storageService.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.storageService.DataTransformationRunning.ToolsConfigProperties;
import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.ProcessingStepMapper;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.TraceabilityDataMapper;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.BlockchainSyncState;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.storageService.MetadataServiceAPI.Services.DatasetService;
import com.ireceptorplus.storageService.MetadataServiceAPI.Services.ProcessingStepService;
import com.ireceptorplus.storageService.iReceptorStorageServiceLogging;
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

    ProcessingStepMapper processingStepMapper;

    ProcessingStepService processingStepService;

    @Autowired
    public TraceabilityDataController(HyperledgerFabricAPI blockchainAPI, FileSystemManager fileSystemManager, ToolsConfigProperties toolsConfigProperties, DatasetService datasetService, TraceabilityDataMapper traceabilityDataMapper, ProcessingStepMapper processingStepMapper, ProcessingStepService processingStepService)
    {
        this.blockchainAPI = blockchainAPI;
        this.fileSystemManager = fileSystemManager;
        this.toolsConfigProperties = toolsConfigProperties;
        this.datasetService = datasetService;
        this.traceabilityDataMapper = traceabilityDataMapper;
        this.processingStepMapper = processingStepMapper;
        this.processingStepService = processingStepService;
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

        ProcessingStep processingStep = processingStepMapper.processingStepDTOToProcessingStep(processingStepDTO);
        processingStep.setBlockchainState(BlockchainSyncState.SUBMITTED);
        processingStepService.save(processingStep);

        try
        {
            TraceabilityDataReturnType returnFromBlockchain = blockchainAPI.createTraceabilityDataEntry(data);
            System.out.println("Traceability data entry for pipeline " + processingStep.getName() + " has been successfully submitted to blockchain.");
            return returnFromBlockchain;
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    @Operation(summary = "Returns all traceability data entries awaiting validation on the blockchain.")
    @GetMapping()
    public List<TraceabilityDataReturnType> getAllTraceabilityDataAwaitingValidationFromBlockchain() throws BlockchainAPIException
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
    public VoteResultReturnType runDataProcessingPipelineAndSubmitVote(@RequestBody @Valid TraceabilityDataReturnType data) throws ErrorComparingOutputs, BlockchainAPIException, TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand, UnsupportedTool
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

    @Operation(summary = "Returns all traceability data entries awaiting validation on the blockchain.")
    @GetMapping("validated")
    public List<TraceabilityDataReturnType> getAllTraceabilityDataValidatedFromBlockchain() throws BlockchainAPIException
    {
        try
        {
            return blockchainAPI.getTraceabilityDataValidated();
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    @Operation(summary = "Returns a string representing the name of the blockchain consortium organization to which this peer belongs.")
    @GetMapping("getOrgName")
    public String getOrgName() throws BlockchainAPIException
    {
        return blockchainAPI.getOrgName();
    }

}
