package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.ArrayList;
import java.util.Objects;

public class ReproducibilityData
{
    /**
     * The source from which the input dataset(s) can be fetched so that the processing may be performed.
     */
    private ArrayList<DownloadbleFile> inputDatasets;

    /**
     * An executable script that allows to reproduce the processing made to the data.
     */
    private ReproducibleScript script;

    /**
     * The source from which the output dataset(s) can be fetched to validate the output of the processing.
     */
    private ArrayList<DownloadbleFile> outputDatasets;

    public ReproducibilityData() {
    }

    public ReproducibilityData(ArrayList<DownloadbleFile> inputDatasets, ReproducibleScript script, ArrayList<DownloadbleFile> outputDatasets)
    {
        this.inputDatasets = inputDatasets;
        this.script = script;
        this.outputDatasets = outputDatasets;
    }

    public ArrayList<DownloadbleFile> getInputDatasets()
    {
        return inputDatasets;
    }

    public ReproducibleScript getScript()
    {
        return script;
    }

    public ArrayList<DownloadbleFile> getOutputDatasets()
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
