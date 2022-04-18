package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.VoteResultReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlockchainAPI
{
    TraceabilityDataReturnType createTraceabilityDataEntry(TraceabilityDataToBeSubmitted data) throws BlockchainAPIException;

    List<TraceabilityDataReturnType> getTraceabilityDataAwaitingValidation() throws BlockchainAPIException;

    VoteResultReturnType submitVote(TraceabilityDataReturnType data, VoteType voteType) throws BlockchainAPIException;
}
