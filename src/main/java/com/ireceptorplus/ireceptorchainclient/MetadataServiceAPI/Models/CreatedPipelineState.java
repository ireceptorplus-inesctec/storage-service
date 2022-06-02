package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models;

public enum CreatedPipelineState
{
    JUST_CREATED(0), IN_QUEUE(1), PROCESSING(2), FINISHED(3);

    private int value;

    private CreatedPipelineState(int value) {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
