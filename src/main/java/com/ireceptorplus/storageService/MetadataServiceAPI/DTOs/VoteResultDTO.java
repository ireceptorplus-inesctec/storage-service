package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;

import com.ireceptorplus.storageService.BlockchainAPI.VoteType;

public class VoteResultDTO
{
    /**
     * The type of vote submitted. Can be YES or NO.
     */
    VoteType voteType;

    /**
     * A message describing the return.
     */
    String message;

    /**
     * A boolean identifying whether the execution has caused the traceability information to change its state.
     */
    boolean stateChange;

    public VoteResultDTO()
    {
    }

    public VoteResultDTO(VoteType voteType, String message, boolean stateChange)
    {
        this.voteType = voteType;
        this.message = message;
        this.stateChange = stateChange;
    }

    public VoteType getVoteType()
    {
        return voteType;
    }

    public void setVoteType(VoteType voteType)
    {
        this.voteType = voteType;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isStateChange()
    {
        return stateChange;
    }

    public void setStateChange(boolean stateChange)
    {
        this.stateChange = stateChange;
    }
}
