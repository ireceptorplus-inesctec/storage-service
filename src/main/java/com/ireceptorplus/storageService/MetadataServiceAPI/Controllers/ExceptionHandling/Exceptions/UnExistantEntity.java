package com.ireceptorplus.storageService.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions;

public class UnExistantEntity extends ApiRequestException
{
    private final Long id;

    public UnExistantEntity(String entityName, Long id)
    {
        super("Can't get " + entityName + " with id " + id);
        this.id = id;
    }
}
