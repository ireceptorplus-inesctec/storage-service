package com.ireceptorplus.storageService.BlockchainAPI.Exceptions;

public class ErrorFetchingData extends HyperledgerFabricException
{
    public ErrorFetchingData(String message)
    {
        super(message);
    }
}
