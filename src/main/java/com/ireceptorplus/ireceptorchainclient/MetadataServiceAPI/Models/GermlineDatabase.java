package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "germline_database")
public class GermlineDatabase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @JsonIgnore
    @Column(name = "data_processings")
    @OneToMany(mappedBy = "germlineDatabase", cascade = CascadeType.ALL)
    private List<DataProcessing> dataProcessings;

    public GermlineDatabase() {}

    public GermlineDatabase(String firstName, String lastName, List<DataProcessing> dataProcessings) {
        this.dataProcessings = dataProcessings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
