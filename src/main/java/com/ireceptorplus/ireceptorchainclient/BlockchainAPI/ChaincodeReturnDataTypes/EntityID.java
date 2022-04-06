package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes;

import java.util.Objects;

/**
 * This class represents the identifier of an entity.
 * It is used to reference an entity from other data type classes.
 * The information about an entity can be easily retrieved from the blockchain, given its ID.
 */
public class EntityID
{
    /**
     * A string representing the id of the entity.
     */
    private String id;

    public String getId()
    {
        return id;
    }

    public EntityID()
    {
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityID entityID = (EntityID) o;
        return id.equals(entityID.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
