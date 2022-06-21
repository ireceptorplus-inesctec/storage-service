package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class ProcessingStep
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "data_processing_id")
    private DataProcessing dataProcessing;

    @ManyToMany()
    private List<Dataset> inputDatasets;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Dataset> outputDatasets;

    @ManyToOne()
    @JoinColumn(name = "command_id", referencedColumnName = "id")
    Command command;

    @Column
    private Long stepOrder;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Date creationDate;

    @Enumerated(EnumType.ORDINAL)
    private ProcessingStepBlockchainState blockchainState;

    public ProcessingStep()
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

    public Command getCommand()
    {
        return command;
    }

    public void setCommand(Command command)
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

    public ProcessingStepBlockchainState getBlockchainState() {
        return blockchainState;
    }

    public void setBlockchainState(ProcessingStepBlockchainState state) {
        this.blockchainState = state;
    }
}
