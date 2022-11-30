package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.ProcessingStepRepository;
import org.springframework.data.jpa.repository.JpaRepository;
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
