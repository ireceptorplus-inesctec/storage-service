package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CreatedPipelineResolver extends ResolverById<CreatedPipeline>
{
    public CreatedPipelineResolver(JpaRepository<CreatedPipeline, Long> repository)
    {
        super(repository);
    }

    @Override
    public CreatedPipeline getNewEntity()
    {
        return new CreatedPipeline();
    }
}

