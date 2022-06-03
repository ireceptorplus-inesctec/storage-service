package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class DownloadbleFile extends File
{
    protected String url;

    public DownloadbleFile(String uuid, String url)
    {
        super(uuid);
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
