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
        System.out.println("Datapath: " + inputsPath);
        String inputsDirAbsolutePath = new java.io.File(inputsPath).getAbsolutePath();
        String outputsDirAbsolutePath = new java.io.File(outputsPath).getAbsolutePath();
        outputsPath = dataDirAbsolutePath;
        String mixcrHostCommand = "docker run -e MI_LICENSE=\"I.eyJ2IjoxNjYyNzI5MTAxLCJlIjoxNjYyODIyOTc2LCJtIjoiNmI2MzY0OWEtNWVmOC00NzBmLTljYjQtNTMwY2QwMmZiYjFlIzdjZDdkMmQzMDdmZTQzNzc0MzcwZTM1OTc5NjQ0OGJkMmY4OGUzMGUyMDNmNzQ2NGI0N2E1ZDZlNWQ3ZWVhODgiLCJsIjoiRS1KQk9MVE9BTktaSEFHQVZLR0xHWUpaVEZKWUlYTERQUlVFV0hWQ01TVkdSWFVXQUQifQ==.CPECUVF.wq7x5xkGpbRCqTpVeMgYK2seydKber9vk9Fiae15KNwYeMTtu9BOjzPCWZxCkYzparTammLKKKIEtIKJyawIm9mhldj1rUJmnjGBWtV4SH2VNFYaYt96OKNCqsiZw5KsEiiH1PQ57D7AAlQMe3Rrvd8dWmjRfRt61d3E13o9C4MhR4FpozYMIVjmwO98ImtecvksrGcuCVyYCIw7dYA4JE7NBODxqY0JSKZl+bVzB3z7ZvTXTZ5B+ed4HrK2vkpJQoDyOaS6ymH/bLuxVcjAyDnwYoA5ytlSE8/Pv+3GhUg3ghvvBvvaWsRC69+sZxBRgNBrvO7afX5R+i4ED6JioA==\" --rm " +
                "    -m 4g " +
                "    -v " + inputsDirAbsolutePath + ":/raw:ro " +
                "    -v " + outputsDirAbsolutePath + ":/work " +
                "    ghcr.io/milaboratory/mixcr/mixcr:4.0.0-251-develop " +
                "    " + buildToolCommandString();

        return mixcrHostCommand;
    }

    @Override
    protected String getOutputsRelativePath()
    {
        return outputsPath;
    }
}
