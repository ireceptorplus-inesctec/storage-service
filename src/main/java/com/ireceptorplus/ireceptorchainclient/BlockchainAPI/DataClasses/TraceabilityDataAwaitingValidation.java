package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses;


import java.util.ArrayList;

/**
 * This is a subclass of TraceabilityData, representing traceability information in a specific state: after the entry was first created and submitted to the blockchain.
 */
public class TraceabilityDataAwaitingValidation extends TraceabilityData
{

    public TraceabilityDataAwaitingValidation(String inputDatasetHashValue,
                                              String outputDatasetHashValue,
                                              EntityID creatorID,
                                              Double value)
    {
        super(inputDatasetHashValue, outputDatasetHashValue, creatorID, value);
        approvers = new ArrayList<>();
        rejecters = new ArrayList<>();
        this.value = value;
    }


}
