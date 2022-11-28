package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Germline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * This identifies the .fasta file in the file system and, therefore, defines the file name.
     */
    @Column(nullable = false)
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

    public Germline()
    {
    }

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
}
