package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DatasetService
{
    @Autowired
    private final DatasetRepository datasetRepository;

    public DatasetService(DatasetRepository datasetRepository)
    {
        this.datasetRepository = datasetRepository;
    }

}
