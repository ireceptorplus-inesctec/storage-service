package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import org.springframework.data.jpa.repository.JpaRepository;

public class ProcessingStepResolver extends ResolverById<ProcessingStep>
{
    public ProcessingStepResolver(JpaRepository<ProcessingStep, Long> repository)
    {
        super(repository);
    }

    @Override
    public ProcessingStep getNewEntity()
    {
        return new ProcessingStep();
    }
}
