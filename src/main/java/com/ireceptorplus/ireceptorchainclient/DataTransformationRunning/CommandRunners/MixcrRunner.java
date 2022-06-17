package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;

import java.util.ArrayList;
import java.util.UUID;

public class MixcrRunner extends CommandRunner
{
    private String outputsPath;

    public MixcrRunner(String dirPath, String inputsFolderPath, ArrayList<File> inputDatasets, String command)
    {
        super(dirPath, inputsFolderPath, inputDatasets, command);
    }

    @Override
    protected String buildToolCommandString()
    {
        //TODO fix hard-coded values
        String datasetsString = "";
        for (File dataset : inputDatasets)
        {
            datasetsString += dataset.getUuid() + ".fasta ";
        }
        String outputDatasetUuid = UUID.randomUUID().toString();
        String outputDatasetName = outputDatasetUuid;
        String outputDatasetFileExtension = ".vdjca";
        File outputFile = new File(outputDatasetUuid, outputDatasetFileExtension);
        outputDatasets.add(outputFile);
        datasetsString += outputDatasetName + outputDatasetFileExtension;

        return "mixcr " + command + " " + datasetsString + " --species hs";
    }

    @Override
    protected String buildHostCommandString(String dataPath)
    {
        String dataDirAbsolutePath = new java.io.File(dataPath).getAbsolutePath();
        outputsPath = dataDirAbsolutePath;
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
