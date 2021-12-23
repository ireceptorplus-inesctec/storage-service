package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;



import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

public class CommandDTO extends DTOWithId
{
    @NotNull
    @JsonProperty("tool_id")
    private Long toolId;

    public CommandDTO()
    {
    }


    public Long getToolId()
    {
        return toolId;
    }

    public void setToolId(Long toolId)
    {
        this.toolId = toolId;
    }
}
