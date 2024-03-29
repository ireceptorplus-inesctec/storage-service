package com.ireceptorplus.storageService.MetadataServiceAPI.Models;

import javax.persistence.*;

@Entity
@Table
public class Command
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Tool tool;

    /**
     * A string representing the command that should be run on the command line.
     */
    @Column(nullable = false)
    private String commandString;

    public Command()
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

    public Tool getTool()
    {
        return tool;
    }

    public void setTool(Tool tool)
    {
        this.tool = tool;
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
