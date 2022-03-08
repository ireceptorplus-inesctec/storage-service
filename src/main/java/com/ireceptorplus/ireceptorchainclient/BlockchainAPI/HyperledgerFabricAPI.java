package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.TraceabilityData.TraceabilityDataAwaitingValidation;

public class HyperledgerFabricAPI implements BlockchainAPI
{
    @Override
    public TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidation()
    {
        return null;
    }

    @Override
    public void submitVote(TraceabilityDataAwaitingValidation data)
    {

    }
}
