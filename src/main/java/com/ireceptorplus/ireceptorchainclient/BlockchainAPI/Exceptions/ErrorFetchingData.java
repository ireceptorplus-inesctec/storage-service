package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions;

public class ErrorFetchingData extends HyperledgerFabricException
{
    public ErrorFetchingData(String message)
    {
        super(message);
    }
}
