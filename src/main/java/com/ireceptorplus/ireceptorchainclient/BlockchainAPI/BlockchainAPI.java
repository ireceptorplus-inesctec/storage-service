package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.TraceabilityDataAwaitingValidation;

public interface BlockchainAPI
{
    TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidation() throws BlockchainAPIException;

    void submitVote(TraceabilityDataAwaitingValidation data, VoteType voteType) throws BlockchainAPIException;
}
