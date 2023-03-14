package com.ireceptorplus.storageService.MetadataServiceAPI.Repositories;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.DataProcessing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataProcessingRepository extends JpaRepository<DataProcessing, Long>
{
}
