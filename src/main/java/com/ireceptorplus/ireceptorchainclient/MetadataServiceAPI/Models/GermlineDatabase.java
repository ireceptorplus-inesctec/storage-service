package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "germline_database")
public class GermlineDatabase
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "data_processings")
    @OneToMany(targetEntity=DataProcessing.class, mappedBy="germlineDatabase")
    private List<DataProcessing> dataProcessings;


    public GermlineDatabase()
    {
    }

    public GermlineDatabase(List<DataProcessing> dataProcessings)
    {
        this.dataProcessings = dataProcessings;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<DataProcessing> getDataProcessings()
    {
        return dataProcessings;
    }

    public void setDataProcessings(List<DataProcessing> dataProcessings)
    {
        this.dataProcessings = dataProcessings;
    }
}
