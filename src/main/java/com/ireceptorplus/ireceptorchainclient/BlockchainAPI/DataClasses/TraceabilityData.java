package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents an entry of traceability information.
 * This is the base class for traceability information.
 * Sub classes are used where there are necessary additional attributes, depending on the state of validation of the traceability information.
 * Please check the VotingRoundStateMachine package for more information.
 */
public abstract class TraceabilityData
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
     * An instance of class EntityID containing information about the id of the entity that created the traceability data entry.
     */
    protected final EntityID creatorID;

    /**
     * An array of entities who have submitted a YES vote for the validity of the traceability information.
     * Each entry contains information about each entity that voted for the traceability information that corresponds to this class.
     */
    protected ArrayList<EntityID> approvers;

    /**
     * An array of entities who have submitted a NO vote for the validity of the traceability information.
     * Each entry contains information about each entity that voted for the traceability information that corresponds to this class.
     */
    protected ArrayList<EntityID> rejecters;

    /**
     * The value of this traceability data that will be used to calculate rewards and penalties for the voters.
     * Optionally, the creator may decide to include an additional reward that will be split among the traceability data validators.
     * The double representing the reward will be available to be consulted even after the traceability data is registered as validated.
     */
    protected Double value;

    public TraceabilityData(String inputDatasetHashValue,
                            String outputDatasetHashValue,
                            EntityID creatorID)
    {
        this.inputDatasetHashValue = inputDatasetHashValue;
        this.outputDatasetHashValue = outputDatasetHashValue;
        this.creatorID = creatorID;
    }

    public TraceabilityData(String inputDatasetHashValue,
                            String outputDatasetHashValue,
                            EntityID creatorID,
                            Double value)
    {
        this.inputDatasetHashValue = inputDatasetHashValue;
        this.outputDatasetHashValue = outputDatasetHashValue;
        this.creatorID = creatorID;
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


    public EntityID getCreatorID()
    {
        return creatorID;
    }

    public ArrayList<EntityID> getApprovers()
    {
        return approvers;
    }

    public ArrayList<EntityID> getRejecters()
    {
        return rejecters;
    }

    public Double getValue()
    {
        return value;
    }

    /**
     * Returns the number of approvers of the traceability information represented by this class.
     * @return the number of approvers of the traceability information represented by this class.
     */
    public long getNumberOfApprovers()
    {
        return approvers.size();
    }

    /**
     * Returns the number of rejecters of the traceability information represented by this class.
     * @return the number of rejecters of the traceability information represented by this class.
     */
    public long getNumberOfRejecters()
    {
        return rejecters.size();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraceabilityData that = (TraceabilityData) o;
        return inputDatasetHashValue.equals(that.inputDatasetHashValue) &&
                outputDatasetHashValue.equals(that.outputDatasetHashValue) &&
                creatorID.equals(that.creatorID) &&
                approvers.equals(that.approvers) &&
                rejecters.equals(that.rejecters);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(inputDatasetHashValue, outputDatasetHashValue, creatorID, approvers, rejecters);
    }
}
