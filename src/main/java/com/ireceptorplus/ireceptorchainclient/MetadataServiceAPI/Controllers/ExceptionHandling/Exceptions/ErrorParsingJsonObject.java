package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions;

public class ErrorParsingJsonObject extends ApiRequestException
{
    public ErrorParsingJsonObject(String message)
    {
        super(message);
    }
}
