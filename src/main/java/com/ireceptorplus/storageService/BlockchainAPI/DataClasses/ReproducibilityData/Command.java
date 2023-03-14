package com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.Objects;

public class Command
{
    /**
     * A String identifying the tool used to run the data processing.
     */
    private String toolId;

    private String commandString;

    public Command() {
    }

    public Command(String toolId, String commandString)
    {
        this.toolId = toolId;
        this.commandString = commandString;
    }

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getCommandString() {
        return commandString;
    }

    public void setCommandString(String commandString) {
        this.commandString = commandString;
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
