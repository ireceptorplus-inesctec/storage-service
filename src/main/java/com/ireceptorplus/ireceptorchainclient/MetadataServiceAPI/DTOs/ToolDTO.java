package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import javax.persistence.Column;

public class ToolDTO
{
    @Column
    private String name;

    @Column
    private String version;

    @Column
    private String description;

    @Column
    private String docsReference;

    public ToolDTO(String name, String version, String description, String docsReference)
    {
        this.name = name;
        this.version = version;
        this.description = description;
        this.docsReference = docsReference;
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
