package com.ireceptorplus.storageService.MetadataServiceAPI.Repositories;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreatedPipelineRepository extends JpaRepository<CreatedPipeline, Long>
{
    @Query("SELECT pipeline FROM CreatedPipeline pipeline WHERE state = 2 LIMIT 1")
    public CreatedPipeline getOneInQueue();
}
