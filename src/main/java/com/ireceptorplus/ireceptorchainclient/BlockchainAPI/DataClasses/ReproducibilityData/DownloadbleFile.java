package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class DownloadbleFile extends File
{
    protected String url;

    protected String hashValue;

    public DownloadbleFile()
    {
    }

    public DownloadbleFile(String uuid, String extension, String url, String hashValue)
    {
        super(uuid, extension);
        this.url = url;
        this.hashValue = hashValue;
    }

    public DownloadbleFile(File file, String url)
    {
        super(file.getUuid(), file.getExtension());
        this.url = url;
    }

    public DownloadbleFile(File file, String url, String hashValue)
    {
        super(file.getUuid(), file.getExtension());
        this.url = url;
        this.hashValue = hashValue;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getHashValue()
    {
        return hashValue;
    }

    public void setHashValue(String hashValue)
    {
        this.hashValue = hashValue;
    }
}
