package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;



import com.sun.istack.NotNull;

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
