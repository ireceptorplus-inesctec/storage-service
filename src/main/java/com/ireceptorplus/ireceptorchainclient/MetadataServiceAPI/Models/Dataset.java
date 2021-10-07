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
}
