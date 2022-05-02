package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.ReproducibleScript;

/**
 * This class implements the logic to determine the file system path structure.
 * It determines the relative path to store the inputs, the expected outputs (the ones registered with the traceability data entry), the obtained outputs (after running the processing) and the script necessary for the data processing.
 */
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
