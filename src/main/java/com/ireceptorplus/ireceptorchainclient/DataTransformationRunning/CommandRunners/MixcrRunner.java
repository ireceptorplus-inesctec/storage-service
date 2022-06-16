package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;

import java.util.ArrayList;
import java.util.UUID;

public class MixcrRunner extends CommandRunner
{
    public MixcrRunner(String dirPath, String inputsFolderPath, ArrayList<File> inputDatasets, String command)
    {
        super(dirPath, inputsFolderPath, inputDatasets, command);
    }

    @Override
    protected String buildToolCommandString()
    {
        String datasetsString = "";
        for (File dataset : inputDatasets)
        {
            datasetsString += dataset.getUuid() + " ";
        }
        String outputDatasetName = UUID.randomUUID().toString();
        datasetsString += outputDatasetName;

        return "mixcr " + command + " " + datasetsString;
    }

    @Override
    protected String buildHostCommandString(String dataPath)
    {
        String dataDirAbsolutePath = new java.io.File(dataPath).getAbsolutePath();
        String mixcrHostCommand = "docker run -it --rm \\\n" +
                "    -m 4g \\\n" +
                "    -v " + dataDirAbsolutePath + ":/work \\\n" +
                "    milaboratory/mixcr:latest \\\n" +
                "    " + buildToolCommandString();

        return mixcrHostCommand;
    }

    @Override
    protected String getOutputsRelativePath()
    {
        return inputsFolderPath;
    }
}
