package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class GermlineDTO
{
    private Long id;

    private String url;

    public GermlineDTO(Long id, String url)
    {
        this.id = id;
        this.url = url;
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
