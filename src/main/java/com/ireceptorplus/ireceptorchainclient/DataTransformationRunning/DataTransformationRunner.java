package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import java.util.ArrayList;

public abstract class DataTransformationRunner
{
    /**
     * The inputs datasets that when applied the processing yield the outputs.
     */
    protected ArrayList<DatasetFile> inputs;

    /**
     * The script that when applied to the inputs yields the outputs.
     */
    protected Script script;

    /**
     * The outputs which are yield when the script is applied to the inputs.
     */
    protected ArrayList<DatasetFile> outputs;

    public DataTransformationRunner(ArrayList<DatasetFile> inputs, Script script) {
        this.inputs = inputs;
        this.script = script;
    }

    public ArrayList<DatasetFile> getOutputs() {
        return outputs;
    }

    abstract void run();

    abstract boolean verifyIfOutputsMatch(DatasetFile datasetFile);

    protected void downloadDatasetAndPlaceItOnDir(DatasetFile datasetFile)
    {
        //TODO
    }

}
