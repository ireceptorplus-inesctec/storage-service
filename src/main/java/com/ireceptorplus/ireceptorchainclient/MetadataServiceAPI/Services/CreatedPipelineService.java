package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.CreatedPipelineRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatedPipelineService extends CreateAndReadService<CreatedPipeline, Long>
{
    CreatedPipelineRepository createdPipelineRepository;


    protected CreatedPipelineService(CreatedPipelineRepository createdPipelineRepository)
    {
        super(createdPipelineRepository);
        this.createdPipelineRepository = createdPipelineRepository;
    }
}
