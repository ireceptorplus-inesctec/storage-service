package com.ireceptorplus.storageService.DataTransformationRunning;

import java.util.ArrayList;

public class Script
{
    protected String name;
    protected String content;

    public Script(ArrayList<String> commands)
    {
        for (String command : commands)
        {
            content = content.concat(command);
        }
    }

    public Script( String name, String content)
    {
        this.name = name;
        this.content = content;
    }

    public String getName()
    {
        return name;
    }

    public String getContent()
    {
        return content;
    }
}
