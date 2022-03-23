package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.TraceabilityData.TraceabilityDataAwaitingValidation;

public interface BlockchainAPI
{
    TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidation();

    void submitVote(TraceabilityDataAwaitingValidation data, VoteType voteType) throws BlockchainAPIException;
}
