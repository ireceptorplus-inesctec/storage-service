package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
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

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Dataset> inputDatasets;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Dataset> outputDatasets;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "script_id", referencedColumnName = "id")
    Script script;

    @Column
    private Long stepOrder;

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

    public Script getScript()
    {
        return script;
    }

    public void setScript(Script script)
    {
        this.script = script;
    }

    public Long getStepOrder()
    {
        return stepOrder;
    }

    public void setStepOrder(Long stepOrder)
    {
        this.stepOrder = stepOrder;
    }
}
