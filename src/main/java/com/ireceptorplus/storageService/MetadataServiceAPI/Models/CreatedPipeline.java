package com.ireceptorplus.storageService.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class CreatedPipeline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    private List<Dataset> inputDatasets;

    @OneToOne()
    @JoinColumn(name = "command_id", referencedColumnName = "id")
    private Command command;

    @Enumerated(EnumType.ORDINAL)
    private CreatedPipelineState state;

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
