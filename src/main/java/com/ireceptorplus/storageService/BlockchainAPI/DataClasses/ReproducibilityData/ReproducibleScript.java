package com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.Objects;

public class ReproducibleScript extends DownloadbleFile
{
    public enum ScriptType {NEXTFLOW, BASH}

    /**
     * An enum type that describes the type of script. Can be either NEXTFLOW or BASH.
     */
    protected final ScriptType scriptType;

    public ReproducibleScript(String uuid, String extension, String url, String hashValue, ScriptType scriptType)
    {
        super(uuid, extension, url, hashValue);
        this.scriptType = scriptType;
    }

    public ReproducibleScript()
    {
        super("", "", "", "");
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

    public ScriptType getScriptType()
    {
        return scriptType;
    }
}
