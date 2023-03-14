package com.ireceptorplus.storageService.BlockchainAPI.Exceptions;

public class ErrorSubmittingData extends HyperledgerFabricException
{
    public ErrorSubmittingData(String message)
    {
        super(message);
    }
}
