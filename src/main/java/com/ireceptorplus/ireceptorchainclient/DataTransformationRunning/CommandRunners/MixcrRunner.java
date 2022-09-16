package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class MixcrRunner extends CommandRunner
{
    private String outputsPath;

    public MixcrRunner(String dirPath, String inputsFolderPath,
                       String outputsFolderPath, ArrayList<File> inputDatasets,
                       String command, FileSystemManager fileSystemManager)
    {
        super(dirPath, inputsFolderPath, outputsFolderPath, inputDatasets, command, fileSystemManager);
    }

    @Override
    protected String buildToolCommandString()
    {
        //TODO fix hard-coded values
        String datasetsString = inputDatasets.stream().map(dataset -> fileSystemManager.getFileName(dataset)).collect(Collectors.joining(" "));
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
        String dataDirAbsolutePath = new java.io.File(inputsPath).getAbsolutePath();
        this.outputsPath = dataDirAbsolutePath;
        String mixcrHostCommand = "docker run --rm " +
                "    -m 4g " +
                "    -v " + dataDirAbsolutePath + ":/work " +
                "    milaboratory/mixcr:latest " +
                "    " + buildToolCommandString();

        return mixcrHostCommand;
    }

    @Override
    protected String getOutputsRelativePath()
    {
        return outputsPath;
    }
}
