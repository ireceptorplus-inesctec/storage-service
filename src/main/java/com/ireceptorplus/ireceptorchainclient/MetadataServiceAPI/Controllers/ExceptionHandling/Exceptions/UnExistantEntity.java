package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions;

public class UnExistantEntity extends ApiRequestException
{
    private final Integer id;

    public UnExistantEntity(String entityName, Integer id)
    {
        super("Can't get " + entityName + " with id " + id);
        this.id = id;
    }
}
