package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStepBlockchainState;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ProcessingStepDTO extends DTOWithId
{
    @NotNull
    @NotEmpty
    private List<DatasetDTO> inputDatasets;

    @NotNull
    @NotEmpty
    private List<DatasetDTO> outputDatasets;

    @NotNull
    @NotEmpty
    private CommandDTO command;

    @NotNull
    private Long stepOrder;

    private String name;

    private String description;

    private Date creationDate;

    private ProcessingStepBlockchainState blockchainState;

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

    public CommandDTO getCommand()
    {
        return command;
    }

    public void setCommand(CommandDTO command)
    {
        this.command = command;
    }

    public Long getStepOrder()
    {
        return stepOrder;
    }

    public void setStepOrder(Long stepOrder)
    {
        this.stepOrder = stepOrder;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public ProcessingStepBlockchainState getBlockchainState()
    {
        return blockchainState;
    }

    public void setBlockchainState(ProcessingStepBlockchainState blockchainState)
    {
        this.blockchainState = blockchainState;
    }
}
