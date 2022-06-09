package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipelineState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreatedPipelineDTO
{
    @NotNull
    @JsonProperty("input_datasets_ids")
    private List<String> inputDatasetsIds;

    @NotNull
    private CommandDTO commandDTO;

    @NotNull
    CreatedPipelineState state;

    public CreatedPipelineDTO()
    {
    }

    public List<String> getInputDatasetsIds()
    {
        return inputDatasetsIds;
    }

    public void setInputDatasetsIds(List<String> inputDatasetsIds)
    {
        this.inputDatasetsIds = inputDatasetsIds;
    }

    public CommandDTO getCommandDTO()
    {
        return commandDTO;
    }

    public void setCommandDTO(CommandDTO commandDTO)
    {
        this.commandDTO = commandDTO;
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
