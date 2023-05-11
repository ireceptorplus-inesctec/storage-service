package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipelineState;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.CreatedPipelineRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<CreatedPipeline> getNextToProcess()
    {
        CreatedPipeline pipelineInQueue = new CreatedPipeline();
        pipelineInQueue.setState(CreatedPipelineState.IN_QUEUE);
        Example<CreatedPipeline> examplePipeline = Example.of(pipelineInQueue);

        return createdPipelineRepository.findAll(examplePipeline);
    }
}
