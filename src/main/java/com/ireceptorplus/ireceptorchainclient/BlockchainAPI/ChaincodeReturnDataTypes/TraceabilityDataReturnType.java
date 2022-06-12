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
     * This is an instance of the class ProcessingDetails which contains information regarding the steps taken to perform the data transformation.
     * These steps are necessary in order to check the veracity of the traceability information.
     */
    protected final ProcessingDetails processingDetails;

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

    public TraceabilityDataReturnType(String uuid,  ProcessingDetails processingDetails, EntityID creatorID,
                                      ArrayList<EntityID> approvers, ArrayList<EntityID> rejecters, Double value)
    {
        this.processingDetails = processingDetails;
        this.creatorID = creatorID;
        this.approvers = approvers;
        this.rejecters = rejecters;
        this.value = value;
    }

    public ProcessingDetails getProcessingDetails()
    {
        return processingDetails;
    }

    public EntityID getCreatorID()
    {
        return creatorID;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public ArrayList<EntityID> getApprovers()
    {
        return approvers;
    }

    public void setApprovers(ArrayList<EntityID> approvers)
    {
        this.approvers = approvers;
    }

    public ArrayList<EntityID> getRejecters()
    {
        return rejecters;
    }

    public void setRejecters(ArrayList<EntityID> rejecters)
    {
        this.rejecters = rejecters;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraceabilityDataReturnType that = (TraceabilityDataReturnType) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid);
    }
}
