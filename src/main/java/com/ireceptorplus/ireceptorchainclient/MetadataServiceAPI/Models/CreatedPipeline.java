package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class CreatedPipeline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {CascadeType.ALL})
    private ArrayList<Dataset> inputDatasets;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "script_id", referencedColumnName = "id")
    Script script;

    @Enumerated(EnumType.ORDINAL)
    CreatedPipelineState state;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ArrayList<Dataset> getInputDatasets()
    {
        return inputDatasets;
    }

    public void setInputDatasets(ArrayList<Dataset> inputDatasets)
    {
        this.inputDatasets = inputDatasets;
    }

    public Script getScript()
    {
        return script;
    }

    public void setScript(Script script)
    {
        this.script = script;
    }

    public CreatedPipelineState getState()
    {
        return state;
    }

    public void setState(CreatedPipelineState state)
    {
        this.state = state;
    }
}
