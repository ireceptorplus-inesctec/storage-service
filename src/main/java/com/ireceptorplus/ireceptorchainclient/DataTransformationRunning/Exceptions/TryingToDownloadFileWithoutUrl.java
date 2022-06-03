package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning.Exceptions;

public class TryingToDownloadFileWithoutUrl extends DataTransformationRunningException
{
    public TryingToDownloadFileWithoutUrl(String message)
    {
        super(message);
    }
}
