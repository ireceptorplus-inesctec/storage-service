package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class DownloadbleFile
{
    protected String hashValue;
    protected String url;

    public DownloadbleFile(String hashValue, String url)
    {
        this.hashValue = hashValue;
        this.url = url;
    }

    public String getHashValue()
    {
        return hashValue;
    }

    public String getUrl()
    {
        return url;
    }
}
