package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

public enum BlockchainSyncState
{
    NOT_SUBMITTED(0), IN_VOTING_ROUND(1), REJECTED(2), ACCEPTED(3);

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
