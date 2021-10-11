package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "data_processing")
public class DataProcessing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "germline_database_id")
    private GermlineDatabase germlineDatabase;

    @OneToMany(mappedBy = "dataProcessing")
    private List<ProcessingStep> processingSteps;

    public DataProcessing()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", employee=" + germlineDatabase +
                '}';
    }
}
