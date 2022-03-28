package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

public class DatasetURL implements DatasetSource
{
    /**
     * A string representing the URL from which the script can be fetched.
     */
    private final String url;

    public String getUrl()
    {
        return url;
    }

    public DatasetURL(String url)
    {
        this.url = url;
    }
}
