package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.File;
import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

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

    public String getProcessingPath(String processingId)
    {
        return "./" + processingId;
    }

    private String getDirPathWithSlashAtTheEnd(String path)
    {
        if (!path.substring(path.length() - 1).equals("/"))
            path = path += "/";

        return path;
    }

    public String getInputsRelativePath(String processingPath)
    {
        return getDirPathWithSlashAtTheEnd(processingPath);
    }

    public String getScriptRelativePath(String processingPath)
    {
        return getDirPathWithSlashAtTheEnd(processingPath);
    }

    public String getExpectedOutputsRelativePath(String processingPath)
    {
        return getPathOfFileRelativeToPath(processingPath, "expectedOutputs");
    }

    public String getProcessedOutputsRelativePath(String processingPath)
    {
        return getPathOfFileRelativeToPath(processingPath, "outputs");
    }

    public String getInputRelativePath(String processingPath, File input)
    {
        return getPathOfFileRelativeToPath(processingPath, getInputFileName(input));
    }

    public String getScriptRelativePath(String processingPath, File script)
    {
        return getPathOfFileRelativeToPath(processingPath, getScriptFileName(script));
    }

    public String getExpectedOutputRelativePath(String processingPath, File expectedOutput)
    {
        return getPathOfFileRelativeToPath(processingPath, getExpectedOutputFileName(expectedOutput));
    }

    public String getProcessedOutputRelativePath(String processingPath, File processedOutput)
    {
        return getProcessedOutputsRelativePath(processingPath) + "/" + getProcessedOutputFileName(processedOutput);
    }

    private String getPathOfFileRelativeToPath(String dirPath, String filePath)
    {
        Path processing = Paths.get(dirPath);
        Path expectedOutputPath = Paths.get(filePath);
        Path finalPath = processing.resolve(expectedOutputPath);

        return finalPath.toString();
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
        return file.getUuid() + "." + file.getExtension();
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
