package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;

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
    private String extension;

    private String originalFileName;

    @NotNull
    private String name;

    private String description;

    private Date creationDate;

    private String url;

    private String sha256Checksum;

    public DatasetDTO()
    {
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getOriginalFileName()
    {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName)
    {
        this.originalFileName = originalFileName;
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

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getSha256Checksum()
    {
        return sha256Checksum;
    }

    public void setSha256Checksum(String sha256Checksum)
    {
        this.sha256Checksum = sha256Checksum;
    }
}