package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class DownloadbleFile
{
    protected String uuid;
    protected String url;

    public DownloadbleFile(String uuid, String url)
    {
        this.uuid = uuid;
        this.url = url;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getUrl()
    {
        return url;
    }
}
