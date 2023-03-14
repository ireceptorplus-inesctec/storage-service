package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.ProcessingStepRepository;
import org.springframework.stereotype.Component;

@Component
public class ProcessingStepResolver extends ResolverById<ProcessingStep>
{
    public ProcessingStepResolver(ProcessingStepRepository repository)
    {
        super(repository);
    }

    @Override
    public ProcessingStep getNewEntity()
    {
        return new ProcessingStep();
    }
}
