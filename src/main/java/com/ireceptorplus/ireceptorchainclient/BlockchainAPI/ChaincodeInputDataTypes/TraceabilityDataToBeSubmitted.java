package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ProcessingDetails;

import java.util.ArrayList;

public class TraceabilityDataToBeSubmitted
{
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
    protected Double additionalValue;

    public TraceabilityDataToBeSubmitted(ProcessingDetails processingDetails)
    {
        this.processingDetails = processingDetails;
        this.additionalValue = 0.0;
    }

    public TraceabilityDataToBeSubmitted(ProcessingDetails processingDetails, Double additionalValue)
    {
        this.processingDetails = processingDetails;
        this.additionalValue = additionalValue;
    }

    public ProcessingDetails getProcessingDetails()
    {
        return processingDetails;
    }

    public Double getValue()
    {
        return additionalValue;
    }
}
