package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tool
{
    @Id
    @Column()
    private Long id;

    @Column
    private String name;

    @Column
    private String version;

    @Column
    private String description;

    @Column
    private String docsReference;


}
