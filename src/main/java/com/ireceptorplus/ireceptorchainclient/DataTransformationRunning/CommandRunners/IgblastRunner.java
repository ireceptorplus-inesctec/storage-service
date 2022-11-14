package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.ToolsConfigProperties;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

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
        String dockerRunCommand = "docker run --rm -m 4g" +
                "   -v " + igblastVolumeName + ":/igblast/files:ro " +
                "   " + igblastContainerTag + " " +
                buildToolCommandString() + " -query " + inputFilePathInsideIgblastContainer;
        String removeContainerCommand = "docker rm -f " + igblastContainerName;

        ArrayList<String> commands = new ArrayList<>();
        commands.add(removeContainerCommand);
        commands.add(dockerBuildCommand);
        commands.add(createVolumeCommand);
        commands.add(createContainerCommand);
        commands.add(copyFilesCommand);
        commands.add(dockerRunCommand);
        commands.add(removeContainerCommand);
        for (String command : commands)
        {
            System.out.println(command);
        }

        String outputDatasetUuid = UUID.randomUUID().toString();
        String outputDatasetFileExtension = "fasta";
        File outputFile = new File(outputDatasetUuid, outputDatasetFileExtension);
        outputDatasets.add(outputFile);

        return commands;
    }
}
