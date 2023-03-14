package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.ToolRepository;
import org.springframework.stereotype.Component;

@Component
public class ToolResolver extends ResolverById<Tool>
{
    ToolRepository toolRepository;

    public ToolResolver(ToolRepository repository)
    {
        super(repository);
        this.toolRepository = repository;
    }

    public Tool resolveByName(String name)
    {
        Tool resolved = toolRepository.findByName(name);

        return resolved;
    }

    @Override
    public Tool getNewEntity()
    {
        return new Tool();
    }
}
