package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipeline;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

public class ScriptDTO extends DTOWithId
{

    @NotNull
    @NotEmpty
    private String uuid;


    public ScriptDTO()
    {
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
