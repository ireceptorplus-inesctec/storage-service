package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.Objects;

public class ReproducibleScript
{
    /**
     * The URL from which the script can be fetched.
     */
    private final ScriptURL url;

    public ReproducibleScript(ScriptURL url)
    {
        this.url = url;
    }

    public ReproducibleScript()
    {
        this.url = new ScriptURL("");
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReproducibleScript that = (ReproducibleScript) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(url);
    }

    public ScriptURL getUrl()
    {
        return url;
    }
}
