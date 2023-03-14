package com.ireceptorplus.storageService.BlockchainAPI.Exceptions;

public class ErrorSubmittingVote extends HyperledgerFabricException
{
    public ErrorSubmittingVote(String message)
    {
        super(message);
    }
}
