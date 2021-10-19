package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProcessingStepDTO extends DTOWithId
{
    @NotNull
    @JsonProperty("input_datasets")
    @NotEmpty
    private List<DatasetDTO> inputDatasets;

    @NotNull
    @JsonProperty("output_datasets")
    @NotEmpty
    private List<DatasetDTO> outputDatasets;

    @NotNull
    @NotEmpty
    private List<CommandDTO> commands;

    @NotNull
    private Long stepOrder;

    @NotNull
    private String command;

    public ProcessingStepDTO()
    {
    }

    public List<DatasetDTO> getInputDatasets()
    {
        return inputDatasets;
    }

    public void setInputDatasets(List<DatasetDTO> inputDatasets)
    {
        this.inputDatasets = inputDatasets;
    }

    public List<DatasetDTO> getOutputDatasets()
    {
        return outputDatasets;
    }

    public void setOutputDatasets(List<DatasetDTO> outputDatasets)
    {
        this.outputDatasets = outputDatasets;
    }

    public List<CommandDTO> getCommands()
    {
        return commands;
    }

    public void setCommands(List<CommandDTO> commands)
    {
        this.commands = commands;
    }

    public Long getStepOrder()
    {
        return stepOrder;
    }

    public void setStepOrder(Long stepOrder)
    {
        this.stepOrder = stepOrder;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }
}
