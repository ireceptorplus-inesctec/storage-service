package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ChaincodeReturnDataTypes;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.EntityData;

public class EntityDataReturnType extends ChaincodeReturnDataType
{
    /**
     * The uuid used to reference the traceability data.
     */
    protected String uuid;
    EntityData entityData;

    public EntityDataReturnType(String uuid,
                                EntityData entityData)
    {
        this.uuid = uuid;
        this.entityData = entityData;
    }

    public EntityData getEntityData()
    {
        return entityData;
    }

    public String getUuid()
    {
        return uuid;
    }
}
