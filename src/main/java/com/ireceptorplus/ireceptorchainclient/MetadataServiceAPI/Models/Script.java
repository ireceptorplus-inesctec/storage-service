package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;

@Entity
@Table
public class Script
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "created_pipeline_id")
    private CreatedPipeline createdPipeline;

    /**
     * A string representing the uuid of the script that can be used to index it in the filesystem.
     */
    @Column
    private String uuid;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public CreatedPipeline getCreatedPipeline()
    {
        return createdPipeline;
    }

    public void setCreatedPipeline(CreatedPipeline createdPipeline)
    {
        this.createdPipeline = createdPipeline;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
