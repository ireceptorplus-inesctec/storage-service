package com.ireceptorplus.storageService.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.storageService.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.storageService.DataTransformationRunning.ToolsConfigProperties;

import java.util.ArrayList;
import java.util.UUID;

public class IgblastRunner extends CommandRunner
{
    private static final String igblastContainerTag = "igblast";
    private static final String igblastVolumeName = "igblast-files-volume";
    private static final String igblastContainerName = "igblast-container";

    public IgblastRunner(String dirPath, String pipelineId, ArrayList<File> inputDatasets,
                         String command, FileSystemManager fileSystemManager,
                         ToolsConfigProperties toolsConfigProperties)
    {
        super(dirPath, pipelineId, inputDatasets, command, fileSystemManager, toolsConfigProperties);
    }

    @Override
    public String getInputFilesRelativePath()
    {
        return dirPath;
    }

    @Override
    public String getOutputFilesRelativePath()
    {
        return dirPath;
    }


    @Override
    protected String buildToolCommandString()
    {
        return command;
    }

    @Override
    protected ArrayList<String> buildHostCommandString(String inputsPath, String outputsPath)
    {
        String inputsDirAbsolutePath = new java.io.File(inputsPath).getAbsolutePath();
        String outputsDirAbsolutePath = new java.io.File(outputsPath).getAbsolutePath();
        System.out.println("inputsDirAbsolutePath: " + inputsDirAbsolutePath);
        System.out.println("outputsDirAbsolutePath: " + outputsDirAbsolutePath);

        String outputDatasetUuid = UUID.randomUUID().toString();
        String outputDatasetFileExtension = "fasta";
        File outputFile = new File(outputDatasetUuid, outputDatasetFileExtension);
        String outputFileName = fileSystemManager.getFileName(outputFile);
        outputDatasets.add(outputFile);

        File inputFile = inputDatasets.get(0);
        String inputFileName = fileSystemManager.getFileName(inputFile);
        String dockerBuildCommand = "docker build -t " + igblastContainerTag + " " + toolsConfigProperties.getIgblastDockerfileLocation();
        String createVolumeCommand = "docker volume create --name " + igblastVolumeName;
        String createContainerCommand = "docker container create --name " + igblastContainerName + " -v " + igblastVolumeName + ":/igblast/files igblast";
        String copyFilesCommand = "docker cp " + inputsDirAbsolutePath + "/. " + igblastContainerName + ":/igblast/files";
        String inputFilePathInsideIgblastContainer = "/igblast/files" + "/" + inputFileName;
        String igblastContainerRunName = igblastContainerName + "-run";
        String dockerRunCommand = "docker run -m 4g --name " + igblastContainerRunName +
                "   -v " + igblastVolumeName + ":/igblast/files " +
                "   " + igblastContainerTag + " " +
                buildToolCommandString() + " -query " + inputFilePathInsideIgblastContainer + " " +
                "   -out /igblast/files/" + outputFileName;
        String copyOutputFileToOutputsDir = "docker cp " + igblastContainerRunName + ":/igblast/files/" + outputFileName + " " + outputsDirAbsolutePath;
        String removeContainerCommand = "docker rm -f " + igblastContainerName;
        String removeContainerRunCommand = "docker rm -f " + igblastContainerRunName;

        ArrayList<String> commands = new ArrayList<>();
        commands.add(removeContainerCommand);
        commands.add(removeContainerRunCommand);
        commands.add(dockerBuildCommand);
        commands.add(createVolumeCommand);
        commands.add(createContainerCommand);
        commands.add(copyFilesCommand);
        commands.add(dockerRunCommand);
        commands.add(copyOutputFileToOutputsDir);
        commands.add(removeContainerCommand);
        commands.add(removeContainerRunCommand);
        for (String command : commands)
        {
            System.out.println(command);
        }

        return commands;
    }
}
