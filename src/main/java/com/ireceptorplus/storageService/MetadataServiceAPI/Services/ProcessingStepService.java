package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.ProcessingStepRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcessingStepService extends CreateAndReadService<ProcessingStep, Long>
{
    ProcessingStepRepository processingStepRepository;

    protected ProcessingStepService(ProcessingStepRepository processingStepRepository)
    {
        super(processingStepRepository);
        this.processingStepRepository = processingStepRepository;
    }

    public ProcessingStep save(ProcessingStep processingStep)
    {
        return processingStepRepository.save(processingStep);
    }
}
