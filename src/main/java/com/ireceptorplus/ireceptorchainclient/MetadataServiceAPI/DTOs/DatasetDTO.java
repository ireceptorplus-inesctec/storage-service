package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DatasetDTO extends DTOWithId
{
    @Column
    private String name;

    @Column
    private String description;

    public DatasetDTO()
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
