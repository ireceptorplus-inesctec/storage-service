package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PipelineDTO extends CreatedPipelineDTO
{
    @NotNull
    private List<String> outputDatasetsUuids;

    public PipelineDTO()
    {
    }

    public List<String> getOutputDatasetsUuids()
    {
        return outputDatasetsUuids;
    }

    public void setOutputDatasetsUuids(List<String> outputDatasetsUuids)
    {
        this.outputDatasetsUuids = outputDatasetsUuids;
    }
}
