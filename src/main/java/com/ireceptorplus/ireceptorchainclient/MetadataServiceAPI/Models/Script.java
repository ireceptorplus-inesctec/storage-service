package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.ReproducibleScript;

import javax.persistence.*;

@Entity
@Table
public class Script
{
    public enum ScriptType {NEXTFLOW, BASH}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "created_pipeline_id")
    private CreatedPipeline createdPipeline;

    /**
     * An enum type that describes the type of script. Can be either NEXTFLOW or BASH.
     */
    @Column
    private ReproducibleScript.ScriptType scriptType;

    /**
     * A string representing the uuid of the script that can be used to index it in the filesystem.
     */
    @Column
    private String uuid;

    /**
     * A string representing the url of the script that can be used to fetch it from another peer.
     */
    @Column
    private String url;

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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public ReproducibleScript.ScriptType getScriptType()
    {
        return scriptType;
    }

    public void setScriptType(ReproducibleScript.ScriptType scriptType)
    {
        this.scriptType = scriptType;
    }
}
