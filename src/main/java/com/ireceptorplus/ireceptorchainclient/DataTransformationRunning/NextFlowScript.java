package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class inherits from Script.
 * The array commands will contain only one command, representing the command to launch the Nextflow script.
 * The string content holds the content of the script.
 */
public class NextFlowScript extends Script
{
    private String name;
    private String content;

    public NextFlowScript(String name, String content)
    {
        super(new ArrayList<>(Arrays.asList(new Command("./nextflow run " + name))));
        this.name = name;
        this.content = content;
    }
}
