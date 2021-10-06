package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "data_processing")
public class DataProcessing
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long repertoireId;
    //private Long readingTechniqueId;

    @JsonIgnore
    @JoinColumn(name = "germline_database_id")
    @ManyToOne
    @Column(name = "germline_database")
    private GermlineDatabase germlineDatabase;


    public DataProcessing()
    {

    }

    public DataProcessing(GermlineDatabase germlineDatabase)
    {
        this.germlineDatabase = germlineDatabase;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    public Long getId()
    {
        return id;
    }

    public GermlineDatabase getGermlineDatabase()
    {
        return germlineDatabase;
    }

    public void setGermlineDatabase(GermlineDatabase germlineDatabase)
    {
        this.germlineDatabase = germlineDatabase;
    }
}
