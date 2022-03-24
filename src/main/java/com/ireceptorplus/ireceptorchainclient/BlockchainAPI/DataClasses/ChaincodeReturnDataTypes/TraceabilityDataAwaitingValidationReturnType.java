package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ChaincodeReturnDataTypes;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.TraceabilityDataAwaitingValidation;

import java.util.Objects;

public class TraceabilityDataAwaitingValidationReturnType extends TraceabilityDataReturnType
{

    /**
     * The uuid used to reference the traceability data.
     */
    protected String uuid;
    /**
     * The traceability data just as it is stored on the blockchain.
     */
    TraceabilityDataAwaitingValidation traceabilityDataAwaitingValidationData;

    public TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidationData()
    {
        return traceabilityDataAwaitingValidationData;
    }

    public TraceabilityDataAwaitingValidationReturnType(String uuid,
                                                        TraceabilityDataAwaitingValidation traceabilityDataAwaitingValidationData)
    {
        this.uuid = uuid;
        this.traceabilityDataAwaitingValidationData = traceabilityDataAwaitingValidationData;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraceabilityDataAwaitingValidationReturnType that = (TraceabilityDataAwaitingValidationReturnType) o;
        return uuid.equals(that.uuid) &&
                traceabilityDataAwaitingValidationData.equals(that.traceabilityDataAwaitingValidationData);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid, traceabilityDataAwaitingValidationData);
    }

    public String getUuid()
    {
        return uuid;
    }
}
