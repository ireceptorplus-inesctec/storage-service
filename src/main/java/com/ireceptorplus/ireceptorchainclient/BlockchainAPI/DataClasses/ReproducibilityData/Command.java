package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.Objects;

public class Command
{
    /**
     * A String identifying the tool used to run the data processing.
     */
    private final String toolId;

    private final String commandString;

    public Command(String toolId, String commandString)
    {
        this.toolId = toolId;
        this.commandString = commandString;
    }

    public String getToolId()
    {
        return toolId;
    }

    public String getCommandString()
    {
        return commandString;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return toolId.equals(command.toolId) && commandString.equals(command.commandString);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(toolId, commandString);
    }
}
