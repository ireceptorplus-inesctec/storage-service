package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class Tool
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String version;

    /**
     * An url from which the executable program can be fetched.
     */
    @Column
    @NotNull
    private String url;

    @Column
    private String description;

    @Column
    private String docsReference;

    public Tool(Long id, String name, String version, String url, String description, String docsReference)
    {
        this.id = id;
        this.name = name;
        this.version = version;
        this.url = url;
        this.description = description;
        this.docsReference = docsReference;
    }

    public Tool()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDocsReference()
    {
        return docsReference;
    }

    public void setDocsReference(String docsReference)
    {
        this.docsReference = docsReference;
    }

    @Override
    public String toString()
    {
        return "Tool{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", docsReference='" + docsReference + '\'' +
                '}';
    }
}
