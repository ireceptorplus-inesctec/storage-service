package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

/**
 * This class represents a downloadable file. Can be either a dataset or a script.
 */
public class File
{
    private String name;
    private String url;

    public File(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }
}
