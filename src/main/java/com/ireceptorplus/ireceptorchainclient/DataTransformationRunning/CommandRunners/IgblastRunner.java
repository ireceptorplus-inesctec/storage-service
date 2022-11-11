package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.ToolsConfigProperties;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class IgblastRunner extends CommandRunner
{
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
        //TODO fix hard-coded values
        String datasetsString = inputDatasets.stream().map(dataset -> "/bbx/mnt/input/" + fileSystemManager.getFileName(dataset)).collect(Collectors.joining(" "));
        datasetsString += " ";
        String outputDatasetUuid = UUID.randomUUID().toString();
        String outputDatasetName = outputDatasetUuid;
        String outputDatasetFileExtension = "vdjca";
        File outputFile = new File(outputDatasetUuid, outputDatasetFileExtension);
        outputDatasets.add(outputFile);
        datasetsString += fileSystemManager.getFileName(outputFile);

        return "" + command + " " + datasetsString + " --species hs";
    }

    @Override
    protected ArrayList<String> buildHostCommandString(String inputsPath, String outputsPath)
    {
        String inputsDirAbsolutePath = new java.io.File(inputsPath).getAbsolutePath();
        String outputsDirAbsolutePath = new java.io.File(outputsPath).getAbsolutePath();
        System.out.println("inputsDirAbsolutePath: " + inputsDirAbsolutePath);
        System.out.println("outputsDirAbsolutePath: " + outputsDirAbsolutePath);
        File inputFile = inputDatasets.get(0);
        String inputFileName = fileSystemManager.getFileName(inputFile);
        String dockerBuildCommand = "docker build -t igblast " + toolsConfigProperties.getIgblastDockerfileLocation();
        String dockerCreateVolumeCommand = "docker volume create --name igblast-files-volume";
        String createContainerCommand = "docker container create --name igblast-container -v igblast-files-volume:/igblast/files igblast";
        String copyFilesCommand = "docker cp " + inputsDirAbsolutePath + " igblast-container:/igblast/files";
        String inputFilePath = inputsDirAbsolutePath + "/" + inputFileName;
        String dockerRunCommand = "docker run --rm -m 4g" +
                "    -v igblast-files-volume:/igblast/files:ro " +
                "    igblast " +
                "  -germline_db_V database/mouse_gl_V -germline_db_J database/mouse_gl_J -germline_db_D database/mouse_gl_D -organism mouse -query " + inputFilePath + " -auxiliary_data optional_file/mouse_gl.aux -show_translation -outfmt 3  ";
        String removeContainerCommand = "docker rm igblast-container";

        ArrayList<String> commands = new ArrayList<>();
        commands.add(dockerBuildCommand);
        commands.add(dockerCreateVolumeCommand);
        commands.add(createContainerCommand);
        commands.add(copyFilesCommand);
        commands.add(dockerRunCommand);
        commands.add(removeContainerCommand);
        for (String command : commands)
        {
            System.out.println(command);
        }


        return commands;
    }
}
