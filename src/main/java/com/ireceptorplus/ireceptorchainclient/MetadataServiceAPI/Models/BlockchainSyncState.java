package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

public enum BlockchainSyncState
{
    NOT_SUBMITTED(0), SUBMITTED(1);

    private int value;

    BlockchainSyncState(int value) {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}
