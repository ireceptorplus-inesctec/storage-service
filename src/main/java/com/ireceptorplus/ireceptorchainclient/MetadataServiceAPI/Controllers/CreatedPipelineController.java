package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.DataTransformationRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorCopyingInputFiles;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorCopyingOutputFiles;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorRunningToolCommand;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.TryingToDownloadFileWithoutUrl;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CreatedPipelineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.CreatedPipelineMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.ScriptMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.*;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.*;
import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jobrunr.scheduling.JobScheduler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class CreatedPipelineController
{
    @Autowired
    private final ScriptService scriptService;

    @Autowired
    private final ScriptMapper scriptMapper;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private final CreatedPipelineService createdPipelineService;

    @Autowired
    private final CreatedPipelineMapper createdPipelineMapper;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private FileSystemManager fileSystemManager;

    @Autowired
    private final DatasetService datasetService;

    @Autowired
    private final DataProcessingService dataProcessingService;

    @Autowired
    private final ProcessingStepService processingStepService;

    @Autowired
    private final ToolService toolService;

    @Autowired
    private final CommandService commandService;

    public CreatedPipelineController(ScriptService scriptService, ScriptMapper scriptMapper, ModelMapper modelMapper, CreatedPipelineService createdPipelineService, CreatedPipelineMapper createdPipelineMapper, JobScheduler jobScheduler, FileSystemManager fileSystemManager, DatasetService datasetService, DataProcessingService dataProcessingService, ProcessingStepService processingStepService, ToolService toolService, CommandService commandService)
    {
        this.scriptService = scriptService;
        this.scriptMapper = scriptMapper;
        this.modelMapper = modelMapper;
        this.createdPipelineService = createdPipelineService;
        this.createdPipelineMapper = createdPipelineMapper;
        this.jobScheduler = jobScheduler;
        this.fileSystemManager = fileSystemManager;
        this.datasetService = datasetService;
        this.dataProcessingService = dataProcessingService;
        this.processingStepService = processingStepService;
        this.toolService = toolService;
        this.commandService = commandService;
    }

    @Operation(summary = "Creates a new CreatedPipeline object")
    @PostMapping("created_pipeline")
    public CreatedPipelineDTO create(@Parameter(description = "The new instance of CreatedPipeline to be created") @RequestBody @Valid CreatedPipelineDTO createdPipelineDTO) throws TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand
    {
        CreatedPipeline createdPipeline = createdPipelineMapper.createdPipelineDTOToCreatedPipeline(createdPipelineDTO);
        ArrayList<Dataset> inputDatasets = new ArrayList<>();
        for (String datasetUuid : createdPipelineDTO.getInputDatasetsUuids())
        {
            Dataset dataset = datasetService.readByUuid(datasetUuid);
            inputDatasets.add(dataset);
        }
        createdPipeline.setInputDatasets(inputDatasets);
        createdPipeline.setState(CreatedPipelineState.JUST_CREATED);
        CreatedPipeline newCreatedPipeline = createdPipelineService.create(createdPipeline);
        CreatedPipelineDTO newCreatedPipelineDTO = createdPipelineMapper.createdPipelineTocreatedPipelineDTO(newCreatedPipeline);

        try
        {
            enqueuePipelineForExecution(createdPipeline);
        } catch (TryingToDownloadFileWithoutUrl e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Trying to download file without URL");
            throw e;
        } catch (ErrorCopyingInputFiles e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error copying input files.");
            throw e;
        } catch (ErrorRunningToolCommand e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error running tool command");
            throw e;
        }

        return newCreatedPipelineDTO;
    }

    private void enqueuePipelineForExecution(CreatedPipeline createdPipeline) throws ErrorCopyingInputFiles, TryingToDownloadFileWithoutUrl, ErrorRunningToolCommand
    {
        createdPipeline.setState(CreatedPipelineState.IN_QUEUE);
        runPipeline(createdPipeline);
    }

    @Async()
    @GetMapping("runPipelines")
    public void runPipeline(CreatedPipeline createdPipeline) throws TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand
    {
        createdPipeline = createdPipelineService.readAll().get(0);
        System.out.println("running pipeline");
        ArrayList<File> inputDatasetFiles = convertDatasetsToFiles(new ArrayList<>(createdPipeline.getInputDatasets()));
        Command commandModel = createdPipeline.getCommand();
        Tool tool = commandModel.getTool();
        com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command command =
                new com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command(tool.getName(), commandModel.getCommandString());

        String processingFilesDirPrefix = "./processingFiles/" + createdPipeline.getId().toString();
        String inputsPath = fileSystemManager.getPathOfFileRelativeToPath(processingFilesDirPrefix, "inputs");
        String outputsPath = fileSystemManager.getPathOfFileRelativeToPath(processingFilesDirPrefix, "outputs");
        System.out.println("inputsPath: " + inputsPath);
        DataTransformationRunner runner = new DataTransformationRunner(inputDatasetFiles,
                command, DataTransformationRunner.RunningMode.COMPUTE_OUTPUTS, tool.getName(),
                inputsPath, outputsPath, fileSystemManager);
        try
        {
            runner.run();
        } catch (TryingToDownloadFileWithoutUrl e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Trying to download file without URL");
            throw e;
        } catch (ErrorCopyingInputFiles e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error copying input files.");
            throw e;
        } catch (ErrorRunningToolCommand e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error running tool command");
            throw e;
        }
        ArrayList<DownloadbleFile> outputsMetadata = runner.getOutputs();
        ArrayList<java.io.File> outputDatasetFiles = runner.getOutputDatasetFiles();
        try
        {
            copyOutputsToDatasetsDir(outputsMetadata, outputDatasetFiles);
        } catch (ErrorCopyingOutputFiles e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error copying output files: " + e.getMessage());
            e.printStackTrace();
        }
        createEntitiesOnDb(outputsMetadata, createdPipeline);
    }

    /**
     * This method copies outputs to the datasets dir.
     * This will ensure other peers can access the datasets if they want to run the pipeline.
     */
    private void copyOutputsToDatasetsDir(ArrayList<DownloadbleFile> outputDatasets,
                                          ArrayList<java.io.File> outputDatasetFiles) throws ErrorCopyingOutputFiles
    {
        for (int i = 0; i < outputDatasets.size() && i < outputDatasetFiles.size(); i++)
        {
            File outputDataset = outputDatasets.get(i);
            java.io.File outputDatasetFile = outputDatasetFiles.get(i);
            String storedFilePath = fileSystemManager.getStoredFilePath(outputDataset);
            java.io.File storedFile = new java.io.File(storedFilePath);
            try
            {
                copyFileUsingChannel(outputDatasetFile, storedFile);
            } catch (IOException e)
            {
                throw new ErrorCopyingOutputFiles(e.getMessage());
            }
        }
    }

    private ArrayList<File> convertDatasetsToFiles(ArrayList<Dataset> inputDatasets)
    {
        ArrayList<File> inputDatasetFiles = new ArrayList<>();
        for (Dataset dataset : inputDatasets)
        {
            UUID uuid = dataset.getUuid();
            String extension = dataset.getExtension();
            DownloadbleFile downloadbleFile = new DownloadbleFile(uuid.toString(), extension, dataset.getUrl());
            inputDatasetFiles.add(downloadbleFile);
        }

        return inputDatasetFiles;
    }

    private static void copyFileUsingChannel(java.io.File source, java.io.File dest) throws IOException
    {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try
        {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally
        {
            sourceChannel.close();
            destChannel.close();
        }
    }

    private void createEntitiesOnDb(ArrayList<DownloadbleFile> outputsMetadata, CreatedPipeline createdPipeline)
    {
        ArrayList<Dataset> outputDatasets = new ArrayList<>();
        for (DownloadbleFile downloadbleFile : outputsMetadata)
        {
            Dataset dataset = new Dataset();
            dataset.setCreationDate(new Date());
            dataset.setExtension(downloadbleFile.getExtension());
            dataset.setOriginalFileName(downloadbleFile.getUuid());
            dataset.setUuid(UUID.fromString(downloadbleFile.getUuid()));
            datasetService.create(dataset);
            outputDatasets.add(dataset);
        }
        List<Dataset> inputDatasets = createdPipeline.getInputDatasets();
        ProcessingStep processingStep = new ProcessingStep();
        processingStep.setInputDatasets(inputDatasets);
        processingStep.setCommand(createdPipeline.getCommand());
        processingStep.setOutputDatasets(outputDatasets);
        processingStep.setName(createdPipeline.getName());
        processingStep.setDescription(createdPipeline.getDescription());
        processingStep.setCreationDate(new Date());
        processingStep.setStepOrder(Long.valueOf(1));
        ArrayList<ProcessingStep> processingSteps = new ArrayList<>();
        processingSteps.add(processingStep);
        DataProcessing dataProcessing = new DataProcessing();
        dataProcessing.setProcessingSteps(processingSteps);
        createdPipelineService.delete(createdPipeline);
        dataProcessingService.create(dataProcessing);
    }

    @GetMapping("finishedPipelines")
    public List<ProcessingStepDTO> getAllFinishedPipelines()
    {
        List<ProcessingStep> processingSteps = processingStepService.readAll();
        List<ProcessingStepDTO> processingStepDTOS = processingSteps.stream().map(processingStep -> modelMapper.map(processingStep, ProcessingStepDTO.class)).collect(Collectors.toList());

        return processingStepDTOS;
    }
    @GetMapping("runningPipelines")
    public List<CreatedPipelineDTO> getAllRunningPipelines()
    {
        List<CreatedPipeline> createdPipelines = createdPipelineService.readAll();
        List<CreatedPipelineDTO> createdPipelineDTOS = createdPipelines.stream().map(createdPipeline -> modelMapper.map(createdPipeline, CreatedPipelineDTO.class)).collect(Collectors.toList());

        return createdPipelineDTOS;
    }

}
