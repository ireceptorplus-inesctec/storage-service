package com.ireceptorplus.storageService.MetadataServiceAPI.Repositories;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.ProcessingStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingStepRepository extends JpaRepository<ProcessingStep, Long>
{
}
