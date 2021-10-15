package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

public class CommandDTO
{
    private Long id;

    @NotNull
    private ProcessingStep processingStep;

    @NotNull
    private Tool tool;

    public CommandDTO()
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

    public ProcessingStep getProcessingStep()
    {
        return processingStep;
    }

    public void setProcessingStep(ProcessingStep processingStep)
    {
        this.processingStep = processingStep;
    }

    public Tool getTool()
    {
        return tool;
    }

    public void setTool(Tool tool)
    {
        this.tool = tool;
    }
}
