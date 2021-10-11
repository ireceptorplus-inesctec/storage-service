package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ProcessingStep
{
    @Id
    private Long id;

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
}
