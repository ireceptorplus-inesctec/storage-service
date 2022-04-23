package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.io.File;
import java.util.ArrayList;

public abstract class DataTransformationRunner
{
    /**
     * The inputs datasets that when applied the processing yield the outputs.
     */
    protected ArrayList<DownloadbleFile> inputs;

    /**
     * The script that when applied to the inputs yields the outputs.
     */
    protected DownloadbleFile scriptFile;

    /**
     * The outputs which are yield when the script is applied to the inputs.
     */
    protected ArrayList<DownloadbleFile> outputs;

    public DataTransformationRunner(ArrayList<DownloadbleFile> inputs, DownloadbleFile scriptFile)
    {
        this.inputs = inputs;
        this.scriptFile = scriptFile;
    }

    public ArrayList<DownloadbleFile> getOutputs() {
        return outputs;
    }

    abstract void run();

    abstract boolean verifyIfOutputsMatch(DatasetFile datasetFile);

    protected void downloadDatasetsAndScriptToProcessingDir()
    {
        String processingFilesPath = "./" + scriptFile.getUuid();
        new File(processingFilesPath).mkdirs();
        FileDownloader inputsDownloader = new FileDownloader(inputs, processingFilesPath);
        inputsDownloader.downloadFilesToDir();
        FileDownloader outputsDownloader = new FileDownloader(outputs, processingFilesPath + "/outputs");
        outputsDownloader.downloadFilesToDir();
        FileDownloader scriptDownloader = new FileDownloader(scriptFile, processingFilesPath);
        scriptDownloader.downloadFilesToDir();
    }



}
