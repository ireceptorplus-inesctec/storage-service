package com.ireceptorplus.storageService.DataTransformationRunning.Exceptions;

public class TryingToDownloadFileWithoutUrl extends DataTransformationRunningException
{
    public TryingToDownloadFileWithoutUrl(String message)
    {
        super(message);
    }
}
