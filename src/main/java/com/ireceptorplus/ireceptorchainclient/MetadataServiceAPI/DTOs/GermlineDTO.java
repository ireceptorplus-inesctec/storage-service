package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class GermlineDTO extends DTOWithId
{
    private String url;

    public GermlineDTO()
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
