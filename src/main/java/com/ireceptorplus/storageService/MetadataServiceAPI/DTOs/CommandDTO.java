package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;



import javax.validation.constraints.NotNull;

public class CommandDTO extends DTOWithId
{
    private Long id;

    @NotNull
    private String toolName;

    @NotNull
    private String commandString;

    public CommandDTO()
    {
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(Long id)
    {
        this.id = id;
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
