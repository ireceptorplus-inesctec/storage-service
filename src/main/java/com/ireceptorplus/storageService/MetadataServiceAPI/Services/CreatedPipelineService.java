package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.CreatedPipelineRepository;
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

    public void delete(CreatedPipeline createdPipeline)
    {
        createdPipelineRepository.delete(createdPipeline);
    }
}
