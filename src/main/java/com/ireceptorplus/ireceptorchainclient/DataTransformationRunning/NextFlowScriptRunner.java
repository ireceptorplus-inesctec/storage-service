package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NextFlowScriptRunner extends DataTransformationRunner
{
    public NextFlowScriptRunner(ArrayList<Dataset> inputs, NextFlowScript script)
    {
        super(inputs, script);
    }

    protected void createNextFlowScript() throws IOException
    {
        String str = "Hello";
        BufferedWriter writer = new BufferedWriter(new FileWriter(script.getContent()));
        writer.write(str);

        writer.close();
    }

    @Override
    void run()
    {
        try
        {
            createNextFlowScript();
        } catch (IOException e)
        {
            System.out.println("Failed to create next flow script on disk.");
            e.printStackTrace();
        }

        for (Dataset dataset : inputs)
        {
            downloadDatasetAndPlaceItOnDir(dataset);
        }

        NextFlowScript nextFlowScript = (NextFlowScript) script;
        BashCommandsRunner bashCommandsRunner = new BashCommandsRunner(inputs, script);
        bashCommandsRunner.runBashCommand(nextFlowScript.getContent());

    }

    @Override
    boolean verifyIfOutputsMatch(Dataset dataset)
    {
        return false;
    }

    public static void main(String args[])
    {
        NextFlowScript script = new NextFlowScript("BasicPipeline", "./nextflow run BasicPipeline");
        NextFlowScriptRunner runner = new NextFlowScriptRunner(null, script);
        runner.run();
    }
}
