package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.EntityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.EntityID;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.VoteResultReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class implements the logic for contacting the blockchain.
 */
@Component
public interface BlockchainAPI
{
    /**
     * Enrolls this user into the registry used on the traceability data system.
     * @return
     */
    public EntityDataReturnType enrollMyself() throws BlockchainAPIException;

    /**
     * Creates a traceability data entry on the blockchain.
     * @param data An instance of class TraceabilityDataToBeSubmitted that represents the traceability data entry to be created on the blockchain.
     * @return An instance of class TraceabilityDataReturnType that represents the traceability data entry that has just been created on the blockchain.
     * @throws BlockchainAPIException  An exception is thrown when there is an error contacting the blockchain or parsing the result. It can be a connection error, blockchain certificate error, user certificate error, permission denied, etc. The exception message and exception class subtype provide detailed information about the error.
     */
    TraceabilityDataReturnType createTraceabilityDataEntry(TraceabilityDataToBeSubmitted data) throws BlockchainAPIException;

    /**
     * Fetches all the Traceability data entries on the blockchain that are awaiting validation.
     * These are the entries that the user can vote for and, eventually, be rewarded if considered correct by the majority of the network.
     * @return A List of TraceabilityDataReturnType representing the traceability data entries that are currently awaiting validation on the blockchain.
     * @throws BlockchainAPIException  An exception is thrown when there is an error contacting the blockchain or parsing the result. It can be a connection error, blockchain certificate error, user certificate error, permission denied, etc. The exception message and exception class subtype provide detailed information about the error.
     */
    List<TraceabilityDataReturnType> getTraceabilityDataAwaitingValidation() throws BlockchainAPIException;

    /**
     * Submits a vote to a traceability data entry on the blockchain.
     * @param uuid The uuid of the traceability data entry to vote for.
     * @param voteType An instance of class VoteType which is an enum type that can be either YES or NO, telling whether the user wants to vote yes or not, respectively, for the traceability data entry.
     * @return An instance of VoteResultReturnType representing the vote just submitted to the blockchain if it was successfully submitted.
     * @throws BlockchainAPIException  An exception is thrown when there is an error contacting the blockchain or parsing the result. It can be a connection error, blockchain certificate error, user certificate error, permission denied, etc. The exception message and exception class subtype provide detailed information about the error.
     */
    VoteResultReturnType submitVote(String uuid, VoteType voteType) throws BlockchainAPIException;
}
