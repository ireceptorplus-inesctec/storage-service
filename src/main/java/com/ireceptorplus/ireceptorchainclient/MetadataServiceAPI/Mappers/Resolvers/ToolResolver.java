package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ToolResolver extends ResolverById<Tool>
{
    public ToolResolver(JpaRepository<Tool, Long> repository)
    {
        super(repository);
    }

    @Override
    public Tool getNewEntity()
    {
        return new Tool();
    }
}
