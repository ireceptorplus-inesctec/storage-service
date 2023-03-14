package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.DataProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessingService extends CreateAndReadService<DataProcessing, Long>
{
    @Autowired
    private final DataProcessingRepository dataProcessingRepository;

    public DataProcessingService(DataProcessingRepository dataProcessingRepository)
    {
        super(dataProcessingRepository);
        this.dataProcessingRepository = dataProcessingRepository;
    }

    public List<DataProcessing> getDataProcessings()
    {
        return new ArrayList<>();
    }
}
