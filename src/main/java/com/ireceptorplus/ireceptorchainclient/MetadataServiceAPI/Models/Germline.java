package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Germline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

/*
    @JsonIgnore
    @Column(name = "data_processings")
    @OneToMany(mappedBy = "germlineDatabase", cascade = CascadeType.ALL)
    private List<DataProcessing> dataProcessings;
*/


    public Germline()
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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
