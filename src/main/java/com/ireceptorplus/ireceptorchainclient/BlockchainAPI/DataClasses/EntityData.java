package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses;

import java.util.Objects;

/**
 * This class represents an entity.
 * Is used to store information about the entities who have validated a traceability information entry.
 */
public class EntityData
{
    /**
     * An instance of class ClientIdentity that hyperledger fabric uses to represent the identity of a client (peer).
     * This contains the its certificate, an id, an msp id (id of the organization it belongs to) and may contain additional attributes created upon creation of the peers' certificate.
     */
    private String id;

    /**
     * This is a counter for the reputation of the entity.
     * This is used as an incentive for validation mechanism.
     * Each time an entity submits a vote decided as valid for a traceability data entry, it earns reputation.
     * If an entity creates fake traceability entries or makes false votes for other traceability data entries, it is highly penalized.
     */
    private Double reputation;

    /**
     * This is a counter for the reputation of the entity that is currently at stake.
     * Reputation is put at stake when the entity votes for a traceability data entry that is still awaiting validation.
     * This reputation may be lost if the entity is decided to be lying (by majority consensus).
     */
    private final Double reputationAtStake;

    public String getId()
    {
        return id;
    }

    public Double getReputation()
    {
        return reputation;
    }

    public Double getReputationAtStake()
    {
        return reputationAtStake;
    }

    /**
     * Constructor used to create a representation of the entity's data that is already stored in the blockchain database.
     * It receives all attributes of an entity.
     * @param id A String representing the id of the peer.
     * @param reputation The current reputation of the entity.
     * @param reputationAtStake The current amount of reputation of the entity that is at stake.
     */
    public EntityData(String id,
                      Double reputation,
                      Double reputationAtStake)
    {
        this.id = id;
        this.reputation = reputation;
        this.reputationAtStake = reputationAtStake;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityData that = (EntityData) o;
        return id.equals(that.id) &&
                reputation.equals(that.reputation) &&
                reputationAtStake.equals(that.reputationAtStake);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, reputation, reputationAtStake);
    }
}
