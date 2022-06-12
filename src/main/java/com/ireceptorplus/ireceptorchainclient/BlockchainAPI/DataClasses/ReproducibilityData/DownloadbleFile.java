package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class DownloadbleFile extends File
{
    protected String url;

    protected String hashValue;

    public DownloadbleFile(String uuid) {
        super(uuid);
    }

    public DownloadbleFile(String uuid, String url)
    {
        super(uuid);
        this.url = url;
    }

    public DownloadbleFile(String uuid, String url, String hashValue)
    {
        super(uuid);
        this.url = url;
        this.hashValue = hashValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }
}
