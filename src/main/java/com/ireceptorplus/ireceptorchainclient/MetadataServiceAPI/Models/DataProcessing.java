package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;


import javax.persistence.*;
import java.util.List;

@Entity
public class DataProcessing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "germline_database_id")
    private Germline germline;

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
                ", employee=" + germline +
                '}';
    }
}
