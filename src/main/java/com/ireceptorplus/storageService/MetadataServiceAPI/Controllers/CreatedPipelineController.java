package com.ireceptorplus.storageService.MetadataServiceAPI.Controllers;

import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.storageService.DataTransformationRunning.DataTransformationRunner;
import com.ireceptorplus.storageService.DataTransformationRunning.Exceptions.*;
import com.ireceptorplus.storageService.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.storageService.DataTransformationRunning.ToolsConfigProperties;
import com.ireceptorplus.storageService.MetadataServiceAPI.Config.EntityManager;
import com.ireceptorplus.storageService.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions.ApiRequestException;
import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.CreatedPipelineDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.FileUrlBuilder;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.CreatedPipelineMapper;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.ScriptMapper;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.*;
import com.ireceptorplus.storageService.MetadataServiceAPI.Services.*;
import com.ireceptorplus.storageService.Utils.FileUtils;
import com.ireceptorplus.storageService.iReceptorStorageServiceLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jobrunr.scheduling.JobScheduler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableAsync
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

    @Autowired
    private ToolsConfigProperties toolsConfigProperties;

    @Autowired
    protected FileUrlBuilder fileUrlBuilder;

    @Autowired
    protected AsyncConfiguration asyncConfiguration;


    public CreatedPipelineController(ScriptService scriptService, ScriptMapper scriptMapper,
                                     ModelMapper modelMapper, CreatedPipelineService createdPipelineService,
                                     CreatedPipelineMapper createdPipelineMapper, JobScheduler jobScheduler,
                                     FileSystemManager fileSystemManager, DatasetService datasetService,
                                     DataProcessingService dataProcessingService, ProcessingStepService processingStepService,
                                     ToolService toolService, CommandService commandService,
                                     ToolsConfigProperties toolsConfigProperties, FileUrlBuilder fileUrlBuilder,
                                     AsyncConfiguration asyncConfiguration)
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
        this.toolsConfigProperties = toolsConfigProperties;
        this.fileUrlBuilder = fileUrlBuilder;
        this.asyncConfiguration = asyncConfiguration;
    }

    @Operation(summary = "Creates a new CreatedPipeline object")
    @PostMapping("created_pipeline")
    public CreatedPipelineDTO create(@Parameter(description = "The new instance of CreatedPipeline to be created") @RequestBody @Valid CreatedPipelineDTO createdPipelineDTO) throws TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand, UnsupportedTool
    {
        CreatedPipeline createdPipeline = createdPipelineMapper.createdPipelineDTOToCreatedPipeline(createdPipelineDTO);
        ArrayList<Dataset> inputDatasets = new ArrayList<>();
        for (String datasetUuid : createdPipelineDTO.getInputDatasetsUuids())
        {
            Dataset dataset = datasetService.readByUuid(datasetUuid);
            inputDatasets.add(dataset);
        }
        createdPipeline.setInputDatasets(inputDatasets);
        createdPipeline.setState(CreatedPipelineState.IN_QUEUE);
        CreatedPipeline newCreatedPipeline = createdPipelineService.create(createdPipeline);
        CreatedPipelineDTO newCreatedPipelineDTO = createdPipelineMapper.createdPipelineTocreatedPipelineDTO(newCreatedPipeline);

        return newCreatedPipelineDTO;
    }

    @Scheduled(fixedRate = 1000)
    public void runNextPipelineInQueue()
    {
        EntityManager.getEntityManager().getTransaction().begin();
        CreatedPipeline createdPipeline = createdPipelineService.getNextToProcess();
        System.out.println("running scheduled job");
        if (createdPipeline == null)
            return;

        createdPipeline.setState(CreatedPipelineState.PROCESSING);

        try
        {
            runPipeline(createdPipeline);
            System.out.println("Pipeline Finished.");
        } catch (TryingToDownloadFileWithoutUrl e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Trying to download file without URL");
        } catch (ErrorCopyingInputFiles e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error copying input files.");
        } catch (ErrorRunningToolCommand e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Error running tool command");
        } catch (UnsupportedTool e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Reference to unsupported tool.");
        }
        EntityManager.getEntityManager().getTransaction().commit();
    }

    public void runPipeline(CreatedPipeline createdPipeline) throws TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand, UnsupportedTool
    {
        System.out.println("running pipeline");
        List<Dataset> inputDatasets = createdPipeline.getInputDatasets();
        System.out.println("Started printing datasets: ");
        for (Dataset dataset : inputDatasets)
        {
            System.out.println(dataset.getUuid());

        }

        System.out.println("Finished printing datasets");
        ArrayList<File> inputDatasetFiles = convertDatasetsToFiles(new ArrayList<>(inputDatasets));
        Command commandModel = createdPipeline.getCommand();
        Tool tool = commandModel.getTool();
        com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.Command command =
                new com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.Command(tool.getName(), commandModel.getCommandString());

        DataTransformationRunner runner = new DataTransformationRunner(inputDatasetFiles,
                command, DataTransformationRunner.RunningMode.COMPUTE_OUTPUTS, createdPipeline.getId(), tool.getName(),
                fileSystemManager, toolsConfigProperties, fileUrlBuilder);
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
        } catch (UnsupportedTool e)
        {
            iReceptorStorageServiceLogging.writeLogMessage(e, "Error running pipeline: Reference to unsupported tool");
            throw e;
        }
        ArrayList<File> outputsMetadata = runner.getOutputs();
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
    private void copyOutputsToDatasetsDir(ArrayList<File> outputDatasets,
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
            DownloadbleFile downloadbleFile = new DownloadbleFile(uuid.toString(), extension, dataset.getUrl(), dataset.getSha256Checksum());
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

    private void createEntitiesOnDb(ArrayList<File> outputsMetadata, CreatedPipeline createdPipeline)
    {
        ArrayList<Dataset> outputDatasets = new ArrayList<>();
        for (File file : outputsMetadata)
        {
            Dataset dataset = new Dataset();
            dataset.setCreationDate(new Date());
            dataset.setExtension(file.getExtension());
            dataset.setOriginalFileName(file.getUuid());
            dataset.setUuid(UUID.fromString(file.getUuid()));
            dataset.setUrl(fileUrlBuilder.buildFromUuid(file.getUuid()));
            String filePath = fileSystemManager.getStoredFilePath(file);
            java.io.File datasetFile = new java.io.File(filePath);
            try
            {
                String sha256Checksum = FileUtils.getFileSHA256Checksum(datasetFile);
                dataset.setSha256Checksum(sha256Checksum);
            } catch (IOException e)
            {
                throw new ApiRequestException("Error computing SHA256 checksum of output dataset file after running processing.");
            } catch (NoSuchAlgorithmException e)
            {
                throw new ApiRequestException("Error computing SHA256 checksum of output dataset file after running processing.");
            }
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
        processingStep.setBlockchainState(BlockchainSyncState.NOT_SUBMITTED);
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
