package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Dataset extends ModelWithId
{

    @Column
    private String url;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

}
