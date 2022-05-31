package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreatedPipelineDTO
{
    @NotNull
    @JsonProperty("input_datasets_ids")
    private List<String> inputDatasetsIds;

    @NotNull
    @JsonProperty("script")
    private ScriptDTO script;

    public CreatedPipelineDTO()
    {
    }

    public ScriptDTO getScript()
    {
        return script;
    }

    public void setScript(ScriptDTO script)
    {
        this.script = script;
    }
}
