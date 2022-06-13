package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipelineState;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreatedPipelineDTO
{
    @NotNull
    @JsonProperty("input_datasets_ids")
    private List<String> inputDatasetsUuids;

    @NotNull
    private CommandDTO commandDTO;

    @NotNull
    CreatedPipelineState state;

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
