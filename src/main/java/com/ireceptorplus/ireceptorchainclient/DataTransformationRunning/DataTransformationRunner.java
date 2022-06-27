package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners.CommandRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners.MixcrRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorComparingOutputs;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorCopyingInputFiles;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.ErrorRunningToolCommand;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.TryingToDownloadFileWithoutUrl;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.FileUrlBuilder;
import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

public class DataTransformationRunner
{
    public enum RunningMode
    {VERIFY, COMPUTE_OUTPUTS}

    /**
     * The inputs datasets that when applied the processing yield the outputs.
     */
    protected ArrayList<File> inputs;

    /**
     * The command that will be run.
     */
    protected Command command;

    /**
     * The outputs which are yield when the script is applied to the inputs.
     */
    protected ArrayList<DownloadbleFile> outputs;

    protected RunningMode runningMode;

    @Value("${peer.network.ipAddress}")
    private String peerIpAddr;

    @Value("${peer.network.port}")
    private String peerPort;

    @Autowired
    FileSystemManager fileSystemManager;

    String toolId;

    String processingFilesPath;

    ArrayList<java.io.File> outputDatasetFiles;


    public DataTransformationRunner(ArrayList<File> inputs, Command command,
                                    RunningMode runningMode, String toolId,
                                    String processingFilesPath, FileSystemManager fileSystemManager)
    {
        this.inputs = inputs;
        this.command = command;
        this.runningMode = runningMode;
        this.toolId = toolId;
        this.processingFilesPath = processingFilesPath;
        this.fileSystemManager = fileSystemManager;
    }

    /**
     * This constructor should be used to run a pipeline using datasets that are not stored locally and need to be downloaded from other peers of the network.
     *
     * @param inputDatasets  An ArrayList containing metadata of the input datasets.
     * @param command         An instance of class Command representing the command that should be run to execute the pipeline.
     * @param outputDatasets An ArrayList containing metadata of the output datasets.
     */
    public DataTransformationRunner(ArrayList<DownloadbleFile> inputDatasets, Command command,
                                    ArrayList<DownloadbleFile> outputDatasets, RunningMode runningMode,
                                    String toolId, String processingFilesPath, FileSystemManager fileSystemManager)
    {
        this.inputs = new ArrayList<File>(inputDatasets);
        this.command = command;
        this.outputs = new ArrayList<>(outputDatasets);
        this.runningMode = runningMode;
        this.toolId = toolId;
        this.processingFilesPath = processingFilesPath;
        this.fileSystemManager = fileSystemManager;
    }

    public ArrayList<DownloadbleFile> getOutputs()
    {
        return outputs;
    }

    public void run() throws TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand
    {
        if (runningMode == RunningMode.VERIFY)
            downloadDatasetsToProcessingDir();
        else
            copyDatasetsFromStorageFolderToProcessingDir();
        //TODO map to appropriate runner
        String inputsRelativePath = fileSystemManager.getInputsRelativePath(processingFilesPath);
        CommandRunner commandRunner = new MixcrRunner(processingFilesPath, inputsRelativePath,
                inputs, command.getCommandString(), fileSystemManager);
        commandRunner.executeCommand();
        if (runningMode == RunningMode.VERIFY)
        {
            try
            {
                verifyIfOutputsMatch();
            } catch (ErrorComparingOutputs e)
            {
                iReceptorStorageServiceLogging.writeLogMessage(e, "Error comparing file outputs of processing.");
            }
        }
        ArrayList<File> outputDatasets = commandRunner.getOutputDatasets();
        outputDatasetFiles = commandRunner.getOutputDatasetFiles();
        ArrayList<DownloadbleFile> outputsMetadata = buildOutputMetadata(outputDatasets);
        this.outputs = new ArrayList<>(outputsMetadata);
    }

    public ArrayList<DownloadbleFile> buildOutputMetadata(ArrayList<File> outputDatasets)
    {
        ArrayList<DownloadbleFile> filesMetadata = new ArrayList<>();
        for (File outputDataset : outputDatasets)
        {
            String uuid = outputDataset.getUuid();
            String url = FileUrlBuilder.buildFromUuid(peerIpAddr, peerPort, uuid);
            DownloadbleFile downloadbleFile = new DownloadbleFile(outputDataset, url);
            filesMetadata.add(downloadbleFile);
        }

        return filesMetadata;
    }

    /**
     * This method verifies if the outputs of the data processing match.
     * It uses the logic and values defined in class FileSystemManager to determine the file system path structure.
     *
     * @return A boolean value identifying whether the outputs match or not.
     * @throws ErrorComparingOutputs Exception thrown when an error comparing the outputs occurs.
     */
    public boolean verifyIfOutputsMatch() throws ErrorComparingOutputs
    {
        for (File output : outputs)
        {
            String expectedOutputRelativePath = fileSystemManager.getExpectedOutputRelativePath(processingFilesPath, output);
            String processedOutputRelativePath = fileSystemManager.getProcessedOutputRelativePath(processingFilesPath, output);
            FileContentComparator comparator = new FileContentComparator(expectedOutputRelativePath, processedOutputRelativePath);
            try
            {
                return comparator.compare();
            } catch (IOException e)
            {
                throw new ErrorComparingOutputs();
            }
        }

        return false;
    }

    /**
     * This method downloads the datasets and the script necessary to run the data processing.
     * The scripts are downloaded from their respective URLs which should point to other peers, instances of the iReceptorChain Storage Service (this application).
     */
    protected void downloadDatasetsToProcessingDir() throws TryingToDownloadFileWithoutUrl
    {
        UUID uuid = UUID.randomUUID();
        this.processingFilesPath = fileSystemManager.getProcessingPath(uuid.toString());
        new java.io.File(processingFilesPath).mkdirs();
        ArrayList<DownloadbleFile> inputsDownloadableFiles = getDownloadbleFiles();
        FileDownloader inputsDownloader = new FileDownloader(inputsDownloadableFiles, processingFilesPath);
        inputsDownloader.downloadFilesToDir();
        FileDownloader outputsDownloader = new FileDownloader(outputs, fileSystemManager.getExpectedOutputsRelativePath(processingFilesPath));
        outputsDownloader.downloadFilesToDir();
    }

    private ArrayList<DownloadbleFile> getDownloadbleFiles() throws TryingToDownloadFileWithoutUrl
    {
        ArrayList<DownloadbleFile> inputsDownloadableFiles = new ArrayList<>();
        for (File file : inputsDownloadableFiles)
        {
            if (!(file instanceof DownloadbleFile))
                throw new TryingToDownloadFileWithoutUrl("Error trying to download file without url. Uuid is " + file.getUuid());

            DownloadbleFile downloadbleFile = (DownloadbleFile) file;
            inputsDownloadableFiles.add(downloadbleFile);
        }
        return inputsDownloadableFiles;
    }

    protected void copyDatasetsFromStorageFolderToProcessingDir() throws ErrorCopyingInputFiles
    {
        new java.io.File(processingFilesPath).mkdirs();
        String datasetsPath = fileSystemManager.getInputsRelativePath(processingFilesPath);
        new java.io.File(datasetsPath).mkdirs();
        for (File inputDataset : inputs)
        {
            String storedDatasetPath = fileSystemManager.getStoredFilePath(inputDataset);
            Path storedFile = new java.io.File(storedDatasetPath).toPath();
            String processingDatasetPath = fileSystemManager.getInputRelativePath(processingFilesPath, inputDataset);
            Path processingFile = new java.io.File(processingDatasetPath).toPath();

            try
            {
                Files.copy(storedFile, processingFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e)
            {
                String message = "Error copying file from local dataset storage folder " + inputDataset.getUuid() + ". Reason: ";
                iReceptorStorageServiceLogging.writeLogMessage(e, message);
                throw new ErrorCopyingInputFiles(message);
            }
        }

    }

    public ArrayList<java.io.File> getOutputDatasetFiles()
    {
        return outputDatasetFiles;
    }
}
