package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

public class File
{
    protected String uuid;

    protected String extension;

    public File()
    {
    }

    public File(String uuid, String extension)
    {
        this.uuid = uuid;
        this.extension = extension;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }
}
