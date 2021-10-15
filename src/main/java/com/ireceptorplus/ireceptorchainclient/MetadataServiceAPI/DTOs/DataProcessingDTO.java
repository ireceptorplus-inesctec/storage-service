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

}
