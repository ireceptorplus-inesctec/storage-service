package com.ireceptorplus.storageService.DataTransformationRunning;

import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;

public class ScriptFile extends DownloadbleFile
{
    public ScriptFile(String uuid, String extension, String url, String hashValue)
    {
        super(uuid, extension, url, hashValue);
    }
}
