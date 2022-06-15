package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DatasetService extends CreateAndReadService<Dataset, Long>
{
    @Autowired
    private final DatasetRepository datasetRepository;

    public DatasetService(DatasetRepository datasetRepository)
    {
        super(datasetRepository);
        this.datasetRepository = datasetRepository;
    }

    public Dataset readByUuid(UUID uuid)
    {
        return datasetRepository.findByUuid(uuid);
    }

    public Dataset readByUuid(String uuid)
    {
        return datasetRepository.findByUuid(UUID.fromString(uuid));
    }
}
