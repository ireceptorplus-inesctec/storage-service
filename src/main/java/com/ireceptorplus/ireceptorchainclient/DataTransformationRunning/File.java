package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class File
{
    private String uuid;
    private String url;

    public File(String uuid, String url)
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
