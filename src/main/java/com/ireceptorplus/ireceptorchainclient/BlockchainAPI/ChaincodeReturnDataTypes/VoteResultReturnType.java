package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes;

public class VoteResultReturnType extends ChaincodeReturnDataType
{
    /**
     * A message describing the return.
     */
    String message;

    /**
     * A boolean identifying whether the execution has caused the traceability information to change its state.
     */
    boolean stateChange;

    public VoteResultReturnType()
    {
    }

    public VoteResultReturnType(String message, boolean stateChange)
    {
        this.message = message;
        this.stateChange = stateChange;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isStateChange()
    {
        return stateChange;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setStateChange(boolean stateChange)
    {
        this.stateChange = stateChange;
    }
}
