package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.ToolsConfigProperties;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class MixcrRunner extends CommandRunner
{
    public MixcrRunner(String dirPath, String inputsFolderPath,
                       String outputsFolderPath, ArrayList<File> inputDatasets,
                       String command, FileSystemManager fileSystemManager,
                       ToolsConfigProperties toolsConfigProperties)
    {
        super(dirPath, inputsFolderPath, outputsFolderPath, inputDatasets, command, fileSystemManager, toolsConfigProperties);
    }

    @Override
    protected String buildToolCommandString()
    {
        //TODO fix hard-coded values
        String datasetsString = inputDatasets.stream().map(dataset -> "/raw/" + fileSystemManager.getFileName(dataset)).collect(Collectors.joining(" "));
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
        String mixcrHostCommand = "docker run -e MI_LICENSE=" + toolsConfigProperties.getMixcrLicense() + " --rm " +
                "    -m 4g " +
                "    -v " + inputsDirAbsolutePath + ":/raw:ro " +
                "    -v " + outputsDirAbsolutePath + ":/work " +
                "    ghcr.io/milaboratory/mixcr/mixcr:latest " +
                "    " + buildToolCommandString();

        return mixcrHostCommand;
    }

    @Override
    protected String getOutputsRelativePath()
    {
        return outputsFolderPath;
    }
}
