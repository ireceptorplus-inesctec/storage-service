package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.CommandRunners;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.FileSystemManager;

import java.util.ArrayList;

public class IgblastRunner extends CommandRunner
{
    public IgblastRunner(String dirPath, String inputsFolderPath, String outputsFolderPath, ArrayList<File> inputDatasets, String command, FileSystemManager fileSystemManager)
    {
        super(dirPath, inputsFolderPath, outputsFolderPath, inputDatasets, command, fileSystemManager);
    }

    @Override
    protected String buildToolCommandString()
    {
        return null;
    }

    @Override
    protected String buildHostCommandString(String inputsPath, String outputsPath)
    {
        return null;
    }

    @Override
    protected String getOutputsRelativePath()
    {
        return null;
    }
}
