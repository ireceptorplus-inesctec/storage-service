package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs;



import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CommandDTO extends DTOWithId
{
    @NotNull
    @JsonProperty("tool_name")
    private String toolName;

    @NotNull
    @JsonProperty("command_string")
    private String commandString;

    public CommandDTO()
    {
    }

    public String getToolName()
    {
        return toolName;
    }

    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }

    public String getCommandString()
    {
        return commandString;
    }

    public void setCommandString(String commandString)
    {
        this.commandString = commandString;
    }
}
