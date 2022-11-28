package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;

public class DatasetFile extends DownloadbleFile
{

    public DatasetFile(String name, String extension, String url, String hashValue)
    {
        super(name, extension, url, hashValue);
    }

}
