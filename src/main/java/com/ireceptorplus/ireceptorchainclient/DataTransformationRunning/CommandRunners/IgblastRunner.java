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
        return null;
    }

    @Override
    public String getOutputFilesRelativePath()
    {
        return null;
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
    protected String buildHostCommandString(String inputsPath, String outputsPath)
    {
        String inputsDirAbsolutePath = new java.io.File(inputsPath).getAbsolutePath();
        String outputsDirAbsolutePath = new java.io.File(outputsPath).getAbsolutePath();
        System.out.println("inputsDirAbsolutePath: " + inputsDirAbsolutePath);
        System.out.println("outputsDirAbsolutePath: " + outputsDirAbsolutePath);
        String igblastHostCommand = "docker run --rm -it $(docker build -q " + toolsConfigProperties.getIgblastDockerfileLocation() +  ") " +
                "    -m 4g " +
                "    -v " + inputsDirAbsolutePath + ":/raw:ro " +
                "    -v " + outputsDirAbsolutePath + ":/work " +
                "    ghcr.io/milaboratory/mixcr/mixcr:latest " +
                "    " + buildToolCommandString();

        return igblastHostCommand;
    }
}
