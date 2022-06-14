package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.ReproducibleScript;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.DataTransformationRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CreatedPipelineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.CreatedPipelineMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.ScriptMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.*;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("api/created_pipeline")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class CreatedPipelineController
{
    @Autowired
    private final ScriptService scriptService;

    @Autowired
    private final ScriptMapper scriptMapper;

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
    private final ToolService toolService;

    public CreatedPipelineController(ScriptService scriptService, ScriptMapper scriptMapper, CreatedPipelineService createdPipelineService, CreatedPipelineMapper createdPipelineMapper, JobScheduler jobScheduler, FileSystemManager fileSystemManager, DatasetService datasetService, DataProcessingService dataProcessingService, ToolService toolService)
    {
        this.scriptService = scriptService;
        this.scriptMapper = scriptMapper;
        this.createdPipelineService = createdPipelineService;
        this.createdPipelineMapper = createdPipelineMapper;
        this.jobScheduler = jobScheduler;
        this.fileSystemManager = fileSystemManager;
        this.datasetService = datasetService;
        this.dataProcessingService = dataProcessingService;
        this.toolService = toolService;
    }

    @Operation(summary = "Creates a new CreatedPipeline object")
    @PostMapping
    public CreatedPipelineDTO create(@Parameter(description = "The new instance of CreatedPipeline to be created") @RequestBody @Valid CreatedPipelineDTO createdPipelineDTO)
    {
        CreatedPipeline createdPipeline = createdPipelineMapper.createdPipelineDTOToCreatedPipeline(createdPipelineDTO);
        Tool tool = this.toolService.readByName(createdPipelineDTO.getCommand().getToolName());
        createdPipeline.getCommand().setTool(tool);
        createdPipeline.setState(CreatedPipelineState.JUST_CREATED);
        CreatedPipeline newCreatedPipeline = createdPipelineService.create(createdPipeline);
        CreatedPipelineDTO newCreatedPipelineDTO = createdPipelineMapper.createdPipelineTocreatedPipelineDTO(newCreatedPipeline);

        enqueuePipelineForExecution(createdPipeline);

        return newCreatedPipelineDTO;
    }

    private void enqueuePipelineForExecution(CreatedPipeline createdPipeline)
    {
        createdPipeline.setState(CreatedPipelineState.IN_QUEUE);
        jobScheduler.enqueue(() -> runPipeline(createdPipeline));
    }

    @Job(name = "Job for executing pipelines", retries = 3)
    public void runPipeline(CreatedPipeline createdPipeline)
    {
        ArrayList<File> inputDatasetFiles = convertDatasetsToFiles(new ArrayList<>(createdPipeline.getInputDatasets()));
        Command commandModel = createdPipeline.getCommand();
        Tool tool = commandModel.getTool();
        com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command command =
                new com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command(tool.getName(), commandModel.getCommandString());

        DataTransformationRunner runner = new DataTransformationRunner(inputDatasetFiles,
                command, DataTransformationRunner.RunningMode.COMPUTE_OUTPUTS, tool.getName());
        ArrayList<DownloadbleFile> outputsMetadata = runner.getOutputsMetadata();
        copyOutputsToDatasetsDir(outputsMetadata);
        createEntitiesOnDb(outputsMetadata, createdPipeline);
    }

    /**
     * This method copies outputs to the datasets dir.
     * This will ensure other peers can access the datasets if they want to run the pipeline.
     *
     * @param outputsMetadata An ArrayList containing the metadata of the outputs.
     */
    private void copyOutputsToDatasetsDir(ArrayList<DownloadbleFile> outputsMetadata)
    {
        for (DownloadbleFile downloadbleFile : outputsMetadata)
        {
            String outputPath = fileSystemManager.getProcessedOutputRelativePath(downloadbleFile);
            String storedFilePath = fileSystemManager.getStoredFilesPath() + "/" + downloadbleFile.getUuid();
            java.io.File outputFile = new java.io.File(outputPath);
            java.io.File storedFile = new java.io.File(storedFilePath);
            try
            {
                copyFileUsingChannel(outputFile, storedFile);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<File> convertDatasetsToFiles(ArrayList<Dataset> inputDatasets)
    {
        ArrayList<File> inputDatasetFiles = new ArrayList<>();
        for (Dataset dataset : inputDatasets)
        {
            UUID uuid = dataset.getUuid();
            DownloadbleFile downloadbleFile = new DownloadbleFile(uuid.toString(), dataset.getUrl());
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
        ArrayList<ProcessingStep> processingSteps = new ArrayList<>();
        processingSteps.add(processingStep);
        DataProcessing dataProcessing = new DataProcessing();
        dataProcessing.setProcessingSteps(processingSteps);
        dataProcessingService.create(dataProcessing);
    }

}
