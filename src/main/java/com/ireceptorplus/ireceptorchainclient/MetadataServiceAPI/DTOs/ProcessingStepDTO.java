package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProcessingStepDTO
{
    private Long id;

    @NotNull
    private DataProcessingDTO dataProcessing;

    @NotNull
    @NotEmpty
    private List<DatasetDTO> inputDatasets;

    @NotNull
    @NotEmpty
    private List<DatasetDTO> outputDatasets;

    @NotNull
    @NotEmpty
    private List<CommandDTO> commands;

    @NotNull
    private Long stepOrder;

    @NotNull
    private String command;
}
