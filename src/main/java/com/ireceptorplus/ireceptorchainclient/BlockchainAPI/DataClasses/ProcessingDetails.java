package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the steps taken to make a data transformation.
 */
public class ProcessingDetails
{
    /**
     * The source from which the input dataset(s) can be fetched so that the processing may be performed.
     */
    private ArrayList<DownloadbleFile> inputDatasets;

    /**
     * The command that should be run on the processing tool to execute the desired data processing.
     */
    private Command command;

    /**
     * The source from which the output dataset(s) can be fetched to validate the output of the processing.
     */
    private ArrayList<DownloadbleFile> outputDatasets;

    public ProcessingDetails() {
    }

    public ProcessingDetails(ArrayList<DownloadbleFile> inputDatasets, Command command, ArrayList<DownloadbleFile> outputDatasets)
    {
        this.inputDatasets = inputDatasets;
        this.command = command;
        this.outputDatasets = outputDatasets;
    }

    public ArrayList<DownloadbleFile> getInputDatasets()
    {
        return inputDatasets;
    }

    public Command getCommand()
    {
        return command;
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
        ProcessingDetails that = (ProcessingDetails) o;
        return inputDatasets.equals(that.inputDatasets) && command.equals(that.command) && outputDatasets.equals(that.outputDatasets);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(inputDatasets, command, outputDatasets);
    }
}
