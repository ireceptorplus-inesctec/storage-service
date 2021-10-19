package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Germline extends ModelWithId
{
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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
