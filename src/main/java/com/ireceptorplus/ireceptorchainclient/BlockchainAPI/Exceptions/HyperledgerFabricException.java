package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions;

public abstract class HyperledgerFabricException extends BlockchainAPIException
{
    public HyperledgerFabricException(String message)
    {
        super(message);
    }
}
