package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.TraceabilityData.TraceabilityDataAwaitingValidation;

public interface BlockchainAPI
{
    TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidation();

    void submitVote(TraceabilityDataAwaitingValidation data);
}
