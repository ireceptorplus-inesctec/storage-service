package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.ProcessingStepRepository;
import org.springframework.data.jpa.repository.JpaRepository;
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
