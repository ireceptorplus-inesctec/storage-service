package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ChaincodeReturnDataTypes.TraceabilityDataAwaitingValidationReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.TraceabilityDataAwaitingValidation;

import java.util.List;

public interface BlockchainAPI
{
    TraceabilityDataAwaitingValidation createTraceabilityDataEntry(TraceabilityDataToBeSubmitted data) throws BlockchainAPIException;

    List<TraceabilityDataAwaitingValidationReturnType> getTraceabilityDataAwaitingValidation() throws BlockchainAPIException;

    void submitVote(TraceabilityDataAwaitingValidationReturnType data, VoteType voteType) throws BlockchainAPIException;
}
