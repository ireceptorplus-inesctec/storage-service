package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

public class ScriptURL implements ScriptSource
{
    /**
     * A string representing the URL from which the script can be fetched.
     */
    private final String url;

    public String getUrl()
    {
        return url;
    }

    public ScriptURL(String url)
    {
        this.url = url;
    }
}
