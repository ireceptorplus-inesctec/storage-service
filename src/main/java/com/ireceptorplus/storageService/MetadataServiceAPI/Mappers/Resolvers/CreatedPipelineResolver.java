package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipeline;
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

