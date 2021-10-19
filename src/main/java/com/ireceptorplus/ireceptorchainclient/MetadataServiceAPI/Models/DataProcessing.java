package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;


import javax.persistence.*;
import java.util.List;

@Entity
public class DataProcessing extends ModelWithId
{
    @ManyToOne
    @JoinColumn(name = "germline_database_id")
    private Germline germline;

    @OneToMany(mappedBy = "dataProcessing")
    private List<ProcessingStep> processingSteps;

    public DataProcessing()
    {

    }

    public Germline getGermline()
    {
        return germline;
    }

    public void setGermline(Germline germline)
    {
        this.germline = germline;
    }

    public List<ProcessingStep> getProcessingSteps()
    {
        return processingSteps;
    }

    public void setProcessingSteps(List<ProcessingStep> processingSteps)
    {
        this.processingSteps = processingSteps;
    }

    @Override
    public String toString()
    {
        return "DataProcessing{" +
                ", germline=" + germline +
                ", processingSteps=" + processingSteps +
                '}';
    }
}
