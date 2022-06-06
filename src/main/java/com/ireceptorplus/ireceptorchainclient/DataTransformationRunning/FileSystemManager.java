package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class implements the logic to determine the file system path structure.
 * It determines the relative path to store the inputs, the expected outputs (the ones registered with the traceability data entry), the obtained outputs (after running the processing) and the script necessary for the data processing.
 */
@Service
public class FileSystemManager
{
    @Autowired
    DatasetStorageProperties datasetStorageProperties;

    public FileSystemManager(DatasetStorageProperties datasetStorageProperties)
    {
        this.datasetStorageProperties = datasetStorageProperties;
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
        return "./expectedOutputs";
    }

    public String getProcessedOutputsRelativePath()
    {
        return "./outputs";
    }

    public String getInputRelativePath(File input)
    {
        return getInputsRelativePath() + getInputFileName(input);
    }

    public String getScriptRelativePath(File script)
    {
        return getScriptRelativePath() + getScriptFileName(script);
    }

    public String getExpectedOutputRelativePath(File expectedOutput)
    {
        return getExpectedOutputsRelativePath() + getExpectedOutputFileName(expectedOutput);
    }

    public String getProcessedOutputRelativePath(File processedOutput)
    {
        return getProcessedOutputsRelativePath() + getProcessedOutputFileName(processedOutput);
    }

    private String getInputFileName(File file)
    {
        return getFileName(file);
    }

    private String getScriptFileName(File file)
    {
        return getFileName(file);
    }

    private String getExpectedOutputFileName(File file)
    {
        return getFileName(file);
    }

    private String getProcessedOutputFileName(File file)
    {
        return getFileName(file);
    }

    private String getFileName(File file)
    {
        return file.getUuid();
    }

    public String getStoredFilesPath()
    {
        return datasetStorageProperties.getLocation();
    }

    public String getStoredFilePath(File file)
    {
        return getStoredFilesPath() + "/" + file.getUuid();
    }
}
