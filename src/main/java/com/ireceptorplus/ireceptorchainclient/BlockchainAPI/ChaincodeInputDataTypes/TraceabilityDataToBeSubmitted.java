package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;

import java.util.ArrayList;

public class TraceabilityDataToBeSubmitted
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

    /**
     * The value of this traceability data that will be used to calculate rewards and penalties for the voters.
     * Optionally, the creator may decide to include an additional reward that will be split among the traceability data validators.
     * The double representing the reward will be available to be consulted even after the traceability data is registered as validated.
     */
    protected Double additionalValue;

    public TraceabilityDataToBeSubmitted()
    {
    }

    public TraceabilityDataToBeSubmitted(ArrayList<DownloadbleFile> inputDatasets, Command command, ArrayList<DownloadbleFile> outputDatasets)
    {
        this.inputDatasets = inputDatasets;
        this.command = command;
        this.outputDatasets = outputDatasets;
        this.additionalValue = 0.0;
    }

    public TraceabilityDataToBeSubmitted(ArrayList<DownloadbleFile> inputDatasets, Command command, ArrayList<DownloadbleFile> outputDatasets, Double additionalValue)
    {
        this.inputDatasets = inputDatasets;
        this.command = command;
        this.outputDatasets = outputDatasets;
        this.additionalValue = additionalValue;
    }

    public ArrayList<DownloadbleFile> getInputDatasets()
    {
        return inputDatasets;
    }

    public void setInputDatasets(ArrayList<DownloadbleFile> inputDatasets)
    {
        this.inputDatasets = inputDatasets;
    }

    public Command getCommand()
    {
        return command;
    }

    public void setCommand(Command command)
    {
        this.command = command;
    }

    public ArrayList<DownloadbleFile> getOutputDatasets()
    {
        return outputDatasets;
    }

    public void setOutputDatasets(ArrayList<DownloadbleFile> outputDatasets)
    {
        this.outputDatasets = outputDatasets;
    }

    public Double getAdditionalValue()
    {
        return additionalValue;
    }

    public void setAdditionalValue(Double additionalValue)
    {
        this.additionalValue = additionalValue;
    }

    public Double getValue()
    {
        return additionalValue;
    }
}
