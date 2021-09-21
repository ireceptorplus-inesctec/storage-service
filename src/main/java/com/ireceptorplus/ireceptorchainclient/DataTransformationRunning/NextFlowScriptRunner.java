package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.util.ArrayList;

public class NextFlowScriptRunner extends DataTransformationRunner
{
    public NextFlowScriptRunner(ArrayList<Dataset> inputs, Script script)
    {
        super(inputs, script);
    }

    @Override
    void run()
    {
        for (Dataset dataset : inputs)
        {
            downloadDatasetAndPlaceItOnDir(dataset);
        }

        NextFlowScript nextFlowScript = (NextFlowScript) script;
        BashCommandsRunner bashCommandsRunner = new BashCommandsRunner(inputs, script);
        bashCommandsRunner.runBashCommand(nextFlowScript.getCommands().get(0));

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
