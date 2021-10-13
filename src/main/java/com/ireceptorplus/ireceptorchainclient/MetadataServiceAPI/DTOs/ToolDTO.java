package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class ToolDTO
{
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String version;

    /**
     * An url from which the executable program can be fetched.
     */
    @NotNull
    @NotEmpty
    private String url;

    private String description;

    private String docsReference;

    public ToolDTO(String name, String version, String url, String description, String docsReference)
    {
        this.name = name;
        this.version = version;
        this.url = url;
        this.description = description;
        this.docsReference = docsReference;
    }

    public ToolDTO()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDocsReference()
    {
        return docsReference;
    }

    public void setDocsReference(String docsReference)
    {
        this.docsReference = docsReference;
    }
}
