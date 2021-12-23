package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DataProcessingDTO extends DTOWithId
{
    @NotNull
    @JsonProperty("germline_id")
    private Long germlineId;

    @NotNull
    @NotEmpty
    @JsonProperty("processing_steps")
    private List<ProcessingStepDTO> processingSteps;

    public DataProcessingDTO()
    {
    }

    public Long getGermlineId()
    {
        return germlineId;
    }

    public void setGermlineId(Long germlineId)
    {
        this.germlineId = germlineId;
    }

    public List<ProcessingStepDTO> getProcessingSteps()
    {
        return processingSteps;
    }

    public void setProcessingSteps(List<ProcessingStepDTO> processingSteps)
    {
        this.processingSteps = processingSteps;
    }
}
