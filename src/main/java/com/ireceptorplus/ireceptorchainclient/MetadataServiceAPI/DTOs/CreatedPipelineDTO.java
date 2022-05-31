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
    private ScriptDTO script;

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

    public ScriptDTO getScript()
    {
        return script;
    }

    public void setScript(ScriptDTO script)
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
