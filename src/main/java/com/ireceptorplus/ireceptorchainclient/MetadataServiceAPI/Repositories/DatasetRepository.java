package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetRepository extends JpaRepository<Dataset, Long>
{
}
