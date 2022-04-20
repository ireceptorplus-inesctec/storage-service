package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.Objects;

public class ReproducibleScript
{
    public enum ScriptType {NEXTFLOW, BASH}

    /**
     * The URL from which the script can be fetched.
     */
    private final String url;

    /**
     * An enum type that describes the type of script. Can be either NEXTFLOW or BASH.
     */
    private final ScriptType scriptType;

    public ReproducibleScript(String url, ScriptType scriptType)
    {
        this.url = url;
        this.scriptType = scriptType;
    }

    public ReproducibleScript()
    {
        this.url = "";
        this.scriptType = null;
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

    public String getUrl()
    {
        return url;
    }
}
