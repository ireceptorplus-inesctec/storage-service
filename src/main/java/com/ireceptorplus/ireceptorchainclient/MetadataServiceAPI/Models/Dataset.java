package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class Dataset
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * This identifies the file in the file system and, therefore, defines the file name.
     */
    @Column
    private UUID uuid;

    /**
     * A String representing the original file name in the computer of the uploader.
     */
    @Column
    private String originalFileName;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Date creationDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
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

    public void setCreationDate(Date createdOn)
    {
        this.creationDate = createdOn;
    }
}
