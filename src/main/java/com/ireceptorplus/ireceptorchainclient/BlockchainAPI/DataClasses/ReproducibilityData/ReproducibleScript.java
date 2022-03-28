package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

public abstract class ReproducibleScript
{
    /**
     * The URL from which the script can be fetched.
     */
    private final ScriptURL url;

    public ReproducibleScript(ScriptURL url)
    {
        this.url = url;
    }
}
