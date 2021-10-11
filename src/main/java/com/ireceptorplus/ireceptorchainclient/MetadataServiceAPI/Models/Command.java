package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;

@Entity
@Table
public class Command
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "processing_step_id")
    private ProcessingStep processingStep;

    @OneToOne
    private Tool tool;

    /**
     * A string representing the command that should be run on the command line.
     */
    @Column
    private String commandString;
}
