package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DataProcessingDTO
{
    private Long id;

    @NotNull
    private Long germlineId;

    @NotNull
    @NotEmpty
    private List<ProcessingStepDTO> processingSteps;

    public DataProcessingDTO()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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
