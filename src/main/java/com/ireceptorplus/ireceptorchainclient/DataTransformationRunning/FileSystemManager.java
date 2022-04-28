package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.ReproducibleScript;

public class FileSystemManager
{
    private static FileSystemManager instance;

    public static FileSystemManager getInstance()
    {
        if (instance == null)
            instance = new FileSystemManager();

        return instance;
    }

    public String getInputsRelativePath()
    {
        return "./";
    }

    public String getScriptRelativePath()
    {
        return "./";
    }

    public String getExpectedOutputsRelativePath()
    {
        return "./outputs";
    }

    public String getProcessedOutputsRelativePath()
    {
        return "./";
    }

    public String getInputRelativePath(DownloadbleFile input)
    {
        return getInputsRelativePath() + getFileName(input);
    }

    public String getScriptRelativePath(ReproducibleScript script)
    {
        return getScriptRelativePath() + getFileName(script);
    }

    public String getExpectedOutputRelativePath(DownloadbleFile expectedOutput)
    {
        return getExpectedOutputsRelativePath() + getFileName(expectedOutput);
    }

    public String getProcessedOutputsRelativePath(DownloadbleFile processedOutput)
    {
        return getProcessedOutputsRelativePath() + getFileName(processedOutput);
    }

    private String getInputFileName(DownloadbleFile file)
    {
        return getFileName(file);
    }

    private String getScriptFileName(DownloadbleFile file)
    {
        return getFileName(file);
    }

    private String getExpectedOutputFileName(DownloadbleFile file)
    {
        return getFileName(file);
    }

    private String getProcessedOutputFileName(DownloadbleFile file)
    {
        return getFileName(file);
    }

    private String getFileName(DownloadbleFile file)
    {
        return file.getHashValue();
    }
}
