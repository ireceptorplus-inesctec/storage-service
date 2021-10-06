package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DataProcessing;

import javax.persistence.*;

@Entity
@Table
public class DataProcessing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long repertoireId;
    //private Long readingTechniqueId;

    //aqui fica o objeto mesmo
    //@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE },mappedBy="question")
    //private long germlineDatabaseId;


    public DataProcessing()
    {

    }


    public void setId(Long id)
    {
        this.id = id;
    }

    @Id
    public Long getId()
    {
        return id;
    }
}
