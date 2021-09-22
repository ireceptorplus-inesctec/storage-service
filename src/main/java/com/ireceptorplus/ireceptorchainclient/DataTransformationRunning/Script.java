package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.util.ArrayList;

public class Script
{
    protected String content;
    protected String name;

    public Script(ArrayList<String> commands)
    {
        for (String command : commands)
        {
            content = content.concat(command);
        }
    }

    public Script(String content, String name)
    {
        this.content = content;
        this.name = name;
    }

    public String getContent()
    {
        return content;
    }

    public String getName()
    {
        return name;
    }
}
