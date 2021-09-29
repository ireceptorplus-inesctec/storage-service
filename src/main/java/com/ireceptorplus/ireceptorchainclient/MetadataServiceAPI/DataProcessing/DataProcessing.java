package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DataProcessing;

import javax.persistence.*;

@Entity
@Table
public class DataProcessing
{
    private Long id;
    private Long repertoireId;
    private Long readingTechniqueId;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE },mappedBy="question")
    private Long germlineDatabaseId;



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
