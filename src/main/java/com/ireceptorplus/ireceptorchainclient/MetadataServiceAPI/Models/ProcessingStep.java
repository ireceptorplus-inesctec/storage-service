package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ProcessingStep extends ModelWithId
{
    @ManyToOne
    @JoinColumn(name = "data_processing_id")
    private DataProcessing dataProcessing;

    @ManyToMany
    private List<Dataset> inputDatasets;

    @ManyToMany
    private List<Dataset> outputDatasets;

    @OneToMany
    private List<Command> commands;

    @Column
    private Long stepOrder;

    @Column
    private String command;

    public ProcessingStep()
    {
    }

    public DataProcessing getDataProcessing()
    {
        return dataProcessing;
    }

    public void setDataProcessing(DataProcessing dataProcessing)
    {
        this.dataProcessing = dataProcessing;
    }

    public List<Dataset> getInputDatasets()
    {
        return inputDatasets;
    }

    public void setInputDatasets(List<Dataset> inputDatasets)
    {
        this.inputDatasets = inputDatasets;
    }

    public List<Dataset> getOutputDatasets()
    {
        return outputDatasets;
    }

    public void setOutputDatasets(List<Dataset> outputDatasets)
    {
        this.outputDatasets = outputDatasets;
    }

    public List<Command> getCommands()
    {
        return commands;
    }

    public void setCommands(List<Command> commands)
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
