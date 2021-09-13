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

    }

    @Override
    boolean verifyIfOutputsMatch(Dataset dataset)
    {
        return false;
    }
}
