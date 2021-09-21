package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.util.ArrayList;

public class Script
{
    protected String content;

    public Script(ArrayList<String> commands)
    {
        for (String command : commands)
        {
            content = content.concat(command);
        }
    }

    public Script(String content)
    {
        this.content = content;
    }

    public String getCommand()
    {
        return content;
    }
}
