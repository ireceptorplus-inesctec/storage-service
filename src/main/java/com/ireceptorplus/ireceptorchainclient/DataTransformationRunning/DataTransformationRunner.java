package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners.CommandRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners.IgblastRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners.MixcrRunner;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions.*;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.FileUrlBuilder;
import com.ireceptorplus.ireceptorchainclient.iReceptorStorageServiceLogging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

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
    protected ArrayList<File> outputs;

    /**
     * The expected outputs for the processing.
     * This is used when the processing is a verification of a previous processing, otherwise empty.
     */
    protected ArrayList<DownloadbleFile> expectedOutputs;

    protected RunningMode runningMode;

    protected FileUrlBuilder fileUrlBuilder;

    private String pipelineId;

    FileSystemManager fileSystemManager;

    String toolId;

    String outputFilesPath;

    ArrayList<java.io.File> outputDatasetFiles;

    ToolsConfigProperties toolsConfigProperties;


    public DataTransformationRunner(ArrayList<File> inputs, Command command,
                                    RunningMode runningMode, Long pipelineId, String toolId,
                                    FileSystemManager fileSystemManager, ToolsConfigProperties toolsConfigProperties,
                                    FileUrlBuilder fileUrlBuilder)
    {
        this.inputs = inputs;
        this.command = command;
        this.runningMode = runningMode;
        this.toolId = toolId;
        this.pipelineId = pipelineId.toString();
        this.fileSystemManager = fileSystemManager;
        this.toolsConfigProperties = toolsConfigProperties;
        this.fileUrlBuilder = fileUrlBuilder;
    }

    /**
     * This constructor should be used to run a pipeline using datasets that are not stored locally and need to be downloaded from other peers of the network.
     *  @param inputDatasets  An ArrayList containing metadata of the input datasets.
     * @param command         An instance of class Command representing the command that should be run to execute the pipeline.
     * @param outputDatasets An ArrayList containing metadata of the output datasets.
     * @param toolsConfigProperties
     */
    public DataTransformationRunner(ArrayList<DownloadbleFile> inputDatasets, Command command,
                                    ArrayList<DownloadbleFile> outputDatasets, RunningMode runningMode,
                                    String toolId, String pipelineId, FileSystemManager fileSystemManager,
                                    ToolsConfigProperties toolsConfigProperties)
    {
        this.inputs = new ArrayList<File>(inputDatasets);
        this.command = command;
        this.expectedOutputs = new ArrayList<>(outputDatasets);
        this.runningMode = runningMode;
        this.toolId = toolId;
        this.pipelineId = pipelineId;
        this.fileSystemManager = fileSystemManager;
        this.toolsConfigProperties = toolsConfigProperties;
    }

    public ArrayList<File> getOutputs()
    {
        return outputs;
    }

    public void run() throws TryingToDownloadFileWithoutUrl, ErrorCopyingInputFiles, ErrorRunningToolCommand, UnsupportedTool
    {
        String processingFilesDirPrefix = "./processingFiles/" + pipelineId;

        createFilesDirs(processingFilesDirPrefix);

        if (runningMode == RunningMode.VERIFY)
            downloadDatasetsToProcessingDir(processingFilesDirPrefix);
        else
            copyDatasetsFromStorageFolderToProcessingDir(processingFilesDirPrefix);


        CommandRunner commandRunner;
        if (toolId.equals("MiXCR"))
            commandRunner = new MixcrRunner(processingFilesDirPrefix, pipelineId,
                    inputs, command.getCommandString(), fileSystemManager, toolsConfigProperties);
        else if(toolId.equals("IgBlast"))
            commandRunner = new IgblastRunner(processingFilesDirPrefix, pipelineId,
                    inputs, command.getCommandString(), fileSystemManager, toolsConfigProperties);
        else
        {
            throw new UnsupportedTool("Reference to unsupported tool: " + toolId);
        }
        this.outputFilesPath = commandRunner.getOutputFilesRelativePath();
        commandRunner.executeCommand();
        ArrayList<File> outputDatasets = commandRunner.getOutputDatasets();
        outputDatasetFiles = commandRunner.getOutputDatasetFiles();
        this.outputs = outputDatasets;

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
    }

    private void createFilesDirs(String processingFilesDirPrefix)
    {
        String inputFilesPath = fileSystemManager.getInputsRelativePath(processingFilesDirPrefix);
        String outputFilesPath = fileSystemManager.getOutputsRelativePath(processingFilesDirPrefix);
        String expectedOutputFilesPath = fileSystemManager.getExpectedOutputsRelativePath(processingFilesDirPrefix);
        new java.io.File(inputFilesPath).mkdirs();
        new java.io.File(outputFilesPath).mkdirs();
        new java.io.File(expectedOutputFilesPath).mkdirs();
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
            String expectedOutputRelativePath = fileSystemManager.getExpectedOutputRelativePath(outputFilesPath, output);
            System.out.println("expectedOutputRelativePath: " + expectedOutputRelativePath);
            String processedOutputRelativePath = fileSystemManager.getProcessedOutputRelativePath(outputFilesPath, output);
            System.out.println("processedOutputRelativePath: " + processedOutputRelativePath);
            FileContentComparator comparator = new FileContentComparator(expectedOutputRelativePath, processedOutputRelativePath);
            try
            {
                boolean match = comparator.compare();
                if (!match)
                    return false;
            } catch (IOException e)
            {
                throw new ErrorComparingOutputs();
            }
        }

        return true;
    }

    /**
     * This method downloads the datasets and the script necessary to run the data processing.
     * The scripts are downloaded from their respective URLs which should point to other peers, instances of the iReceptorChain Storage Service (this application).
     */
    protected void downloadDatasetsToProcessingDir(String processingPath) throws TryingToDownloadFileWithoutUrl
    {
        String inputFilesPath = processingPath;
        new java.io.File(inputFilesPath).mkdirs();
        ArrayList<DownloadbleFile> inputsDownloadableFiles = getInputsAsDownloadbleFiles();
        FileDownloader inputsDownloader = new FileDownloader(inputsDownloadableFiles, inputFilesPath);
        inputsDownloader.downloadFilesToDir();
        FileDownloader outputsDownloader = new FileDownloader(expectedOutputs, fileSystemManager.getExpectedOutputsRelativePath(inputFilesPath));
        outputsDownloader.downloadFilesToDir();
    }

    private ArrayList<DownloadbleFile> getInputsAsDownloadbleFiles() throws TryingToDownloadFileWithoutUrl
    {
        ArrayList<DownloadbleFile> inputsDownloadableFiles = new ArrayList<>();
        for (File file : this.inputs)
        {
            if (!(file instanceof DownloadbleFile))
                throw new TryingToDownloadFileWithoutUrl("Error trying to download file without url. Uuid is " + file.getUuid());

            DownloadbleFile downloadbleFile = (DownloadbleFile) file;
            inputsDownloadableFiles.add(downloadbleFile);
        }
        return inputsDownloadableFiles;
    }

    protected void copyDatasetsFromStorageFolderToProcessingDir(String processingPath) throws ErrorCopyingInputFiles
    {
        String inputFilesPath = processingPath;
        new java.io.File(inputFilesPath).mkdirs();
        String datasetsPath = fileSystemManager.getInputsRelativePath(inputFilesPath);
        new java.io.File(datasetsPath).mkdirs();
        for (File inputDataset : inputs)
        {
            String storedDatasetPath = fileSystemManager.getStoredFilePath(inputDataset);
            Path storedFile = new java.io.File(storedDatasetPath).toPath();
            String processingDatasetPath = fileSystemManager.getInputRelativePath(inputFilesPath, inputDataset);
            java.io.File fileToBeProcessed = new java.io.File(processingDatasetPath);
            fileToBeProcessed.mkdirs();
            Path processingFile = fileToBeProcessed.toPath();
                System.out.println("processingDatasetPath");
                System.out.println(processingDatasetPath);

            try
            {
                Files.copy(storedFile, processingFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("copied successfully");
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
