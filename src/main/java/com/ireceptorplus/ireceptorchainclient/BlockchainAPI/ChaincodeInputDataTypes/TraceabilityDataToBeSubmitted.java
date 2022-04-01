package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ProcessingDetails;

import java.util.ArrayList;

public class TraceabilityDataToBeSubmitted
{
    /**
     * The hash value of the input dataset used to perform the data transformation.
     * This is used to validate the integrity of the input dataset, in order to be able to verify the traceability information.
     */
    protected final String inputDatasetHashValue;

    /**
     * The hash value of the output dataset used to perform the data transformation.
     * This is used to validate the integrity of the input dataset, in order to be able to verify the traceability information.
     */
    protected final String outputDatasetHashValue;

    /**
     * This is an instance of the class ProcessingDetails which contains information regarding the steps taken to perform the data transformation.
     * These steps are necessary in order to check the veracity of the traceability information.
     */
    protected final ProcessingDetails processingDetails;

    /**
     * The value of this traceability data that will be used to calculate rewards and penalties for the voters.
     * Optionally, the creator may decide to include an additional reward that will be split among the traceability data validators.
     * The double representing the reward will be available to be consulted even after the traceability data is registered as validated.
     */
    protected Double value;

    public TraceabilityDataToBeSubmitted(String inputDatasetHashValue, String outputDatasetHashValue, ProcessingDetails processingDetails)
    {
        this.inputDatasetHashValue = inputDatasetHashValue;
        this.outputDatasetHashValue = outputDatasetHashValue;
        this.processingDetails = processingDetails;
    }

    public TraceabilityDataToBeSubmitted(String inputDatasetHashValue, String outputDatasetHashValue, ProcessingDetails processingDetails, Double value)
    {
        this.inputDatasetHashValue = inputDatasetHashValue;
        this.outputDatasetHashValue = outputDatasetHashValue;
        this.processingDetails = processingDetails;
        this.value = value;
    }

    public String getInputDatasetHashValue()
    {
        return inputDatasetHashValue;
    }

    public String getOutputDatasetHashValue()
    {
        return outputDatasetHashValue;
    }

    public ProcessingDetails getProcessingDetails()
    {
        return processingDetails;
    }

    public Double getValue()
    {
        return value;
    }
}
