package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses;

public class TraceabilityDataToBeSubmitted extends TraceabilityData
{
    public TraceabilityDataToBeSubmitted(String inputDatasetHashValue,
                                         String outputDatasetHashValue)
    {
        super(inputDatasetHashValue, outputDatasetHashValue, new NullEntityID());
    }

    public TraceabilityDataToBeSubmitted(String inputDatasetHashValue,
                                         String outputDatasetHashValue,
                                         Double value)
    {
        super(inputDatasetHashValue, outputDatasetHashValue, new NullEntityID(), value);
    }
}
