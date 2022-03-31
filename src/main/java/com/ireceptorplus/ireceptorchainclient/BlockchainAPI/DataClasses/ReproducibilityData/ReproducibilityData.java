package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.ArrayList;
import java.util.Objects;

public class ReproducibilityData
{
    /**
     * The source from which the input dataset(s) can be fetched so that the processing may be performed.
     */
    private final ArrayList<DatasetURL> inputDatasets;

    /**
     * An executable script that allows to reproduce the processing made to the data.
     */
    private final ReproducibleScript script;

    /**
     * The source from which the output dataset(s) can be fetched to validate the output of the processing.
     */
    private final ArrayList<DatasetURL> outputDatasets;

    public ReproducibilityData(ArrayList<DatasetURL> inputDatasets, ReproducibleScript script, ArrayList<DatasetURL> outputDatasets)
    {
        this.inputDatasets = inputDatasets;
        this.script = script;
        this.outputDatasets = outputDatasets;
    }

    public ArrayList<DatasetURL> getInputDatasets()
    {
        return inputDatasets;
    }

    public ReproducibleScript getScript()
    {
        return script;
    }

    public ArrayList<DatasetURL> getOutputDatasets()
    {
        return outputDatasets;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReproducibilityData that = (ReproducibilityData) o;
        return inputDatasets.equals(that.inputDatasets) && script.equals(that.script) && outputDatasets.equals(that.outputDatasets);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(inputDatasets, script, outputDatasets);
    }
}
