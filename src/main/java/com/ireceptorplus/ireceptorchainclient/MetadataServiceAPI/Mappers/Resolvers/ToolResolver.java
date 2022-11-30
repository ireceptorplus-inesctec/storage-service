package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.ToolRepository;
import org.springframework.data.jpa.repository.JpaRepository;
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
