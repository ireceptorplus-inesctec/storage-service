package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ProcessingDetails;

import java.util.ArrayList;
import java.util.Objects;

public class TraceabilityDataReturnType extends ChaincodeReturnDataType
{
    /**
     * The uuid used to reference the traceability data entry.
     */
    protected String uuid;

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
     * An instance of class EntityID containing information about the id of the entity that created the traceability data entry.
     */
    protected final String creatorID;

    /**
     * An array of entities who have submitted a YES vote for the validity of the traceability information.
     * Each entry contains information about each entity that voted for the traceability information that corresponds to this class.
     */
    protected ArrayList<String> approvers;

    /**
     * An array of entities who have submitted a NO vote for the validity of the traceability information.
     * Each entry contains information about each entity that voted for the traceability information that corresponds to this class.
     */
    protected ArrayList<String> rejecters;

    /**
     * The value of this traceability data that will be used to calculate rewards and penalties for the voters.
     * Optionally, the creator may decide to include an additional reward that will be split among the traceability data validators.
     * The double representing the reward will be available to be consulted even after the traceability data is registered as validated.
     */
    protected Double value;

    public TraceabilityDataReturnType(String uuid, String inputDatasetHashValue, String outputDatasetHashValue, ProcessingDetails processingDetails, String creatorID, ArrayList<String> approvers, ArrayList<String> rejecters, Double value)
    {
        this.uuid = uuid;
        this.inputDatasetHashValue = inputDatasetHashValue;
        this.outputDatasetHashValue = outputDatasetHashValue;
        this.processingDetails = processingDetails;
        this.creatorID = creatorID;
        this.approvers = approvers;
        this.rejecters = rejecters;
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraceabilityDataReturnType that = (TraceabilityDataReturnType) o;
        return uuid.equals(that.uuid) && inputDatasetHashValue.equals(that.inputDatasetHashValue) && outputDatasetHashValue.equals(that.outputDatasetHashValue) && processingDetails.equals(that.processingDetails) && creatorID.equals(that.creatorID) && approvers.equals(that.approvers) && rejecters.equals(that.rejecters) && value.equals(that.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid, inputDatasetHashValue, outputDatasetHashValue, processingDetails, creatorID, approvers, rejecters, value);
    }

    public String getUuid()
    {
        return uuid;
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

    public String getCreatorID()
    {
        return creatorID;
    }

    public ArrayList<String> getApprovers()
    {
        return approvers;
    }

    public ArrayList<String> getRejecters()
    {
        return rejecters;
    }

    public Double getValue()
    {
        return value;
    }
}
