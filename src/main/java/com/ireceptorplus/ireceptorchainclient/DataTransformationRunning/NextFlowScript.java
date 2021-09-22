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
    private static final String commandPrefixToRunScript = "./nextflow run ";

    public NextFlowScript(String content, String name)
    {
        super(content, name);
    }

}
