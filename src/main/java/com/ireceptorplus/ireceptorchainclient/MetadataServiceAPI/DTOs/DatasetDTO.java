package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class DatasetDTO extends DTOWithId
{
    /**
     * This identifies the file in the file system and, therefore, defines the file name.
     */
    private UUID uuid;

    @NotNull
    private String name;

    private String description;

    private Date creationDate;

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

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }
}
