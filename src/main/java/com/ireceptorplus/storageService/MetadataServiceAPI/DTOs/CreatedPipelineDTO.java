package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipelineState;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class CreatedPipelineDTO
{
    private String name;
    private String description;

    @NotNull
    private List<String> inputDatasetsUuids;

    @NotNull
    private Long commandId;

    CreatedPipelineState state;

    private Date creationDate;

    private CommandDTO command;

    public CreatedPipelineDTO()
    {
    }

    public List<String> getInputDatasetsUuids()
    {
        return inputDatasetsUuids;
    }

    public void setInputDatasetsUuids(List<String> inputDatasetsUuids)
    {
        this.inputDatasetsUuids = inputDatasetsUuids;
    }

    public Long getCommandId()
    {
        return commandId;
    }

    public void setCommandId(Long commandId)
    {
        this.commandId = commandId;
    }

    public CreatedPipelineState getState()
    {
        return state;
    }

    public void setState(CreatedPipelineState state)
    {
        this.state = state;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
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

    public CommandDTO getCommand()
    {
        return command;
    }

    public void setCommand(CommandDTO command)
    {
        this.command = command;
    }
}
