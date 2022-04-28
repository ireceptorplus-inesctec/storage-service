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
        return getInputsRelativePath() + getInputFileName(input);
    }

    public String getScriptRelativePath(ReproducibleScript script)
    {
        return getScriptRelativePath() + getScriptFileName(script);
    }

    public String getExpectedOutputRelativePath(DownloadbleFile expectedOutput)
    {
        return getExpectedOutputsRelativePath() + getExpectedOutputFileName(expectedOutput);
    }

    public String getProcessedOutputRelativePath(DownloadbleFile processedOutput)
    {
        return getProcessedOutputsRelativePath() + getProcessedOutputFileName(processedOutput);
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
