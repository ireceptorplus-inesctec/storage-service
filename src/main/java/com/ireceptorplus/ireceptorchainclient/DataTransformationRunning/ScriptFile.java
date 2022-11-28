package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;

public class ScriptFile extends DownloadbleFile
{
    public ScriptFile(String uuid, String extension, String url, String hashValue)
    {
        super(uuid, extension, url, hashValue);
    }
}
