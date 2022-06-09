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
    private List<Dataset> inputDatasets;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "command_id", referencedColumnName = "id")
    Command command;

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

    public List<Dataset> getInputDatasets()
    {
        return inputDatasets;
    }

    public void setInputDatasets(List<Dataset> inputDatasets)
    {
        this.inputDatasets = inputDatasets;
    }

    public Command getCommand()
    {
        return command;
    }

    public void setCommand(Command command)
    {
        this.command = command;
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
