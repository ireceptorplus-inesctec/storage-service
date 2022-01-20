package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions;

public class ApiRequestException extends RuntimeException
{
    public ApiRequestException(String message)
    {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
