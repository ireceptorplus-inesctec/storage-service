package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "data_processing")
public class DataProcessing
{
    @Id
    private Long id;

    @JsonIgnore //keeps the endpoint, that we will create, from serializing the customer details multiple times
    @ManyToOne
    @JoinColumn(name = "germline_database_id")
    private GermlineDatabase germlineDatabase;



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
