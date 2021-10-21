package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.ProcessingStepResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingStepRepository extends JpaRepository<ProcessingStep, Long>
{
}
