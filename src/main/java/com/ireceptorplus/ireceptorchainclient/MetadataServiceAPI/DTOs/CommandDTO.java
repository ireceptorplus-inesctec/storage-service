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
}
