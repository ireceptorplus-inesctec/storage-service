package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Dataset
{
    @Id
    private Long id;

    @Column
    private String url;

    @ManyToOne
    private ProcessingStep processingStep;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public ProcessingStep getProcessingStep()
    {
        return processingStep;
    }

    public void setProcessingStep(ProcessingStep processingStep)
    {
        this.processingStep = processingStep;
    }
}
