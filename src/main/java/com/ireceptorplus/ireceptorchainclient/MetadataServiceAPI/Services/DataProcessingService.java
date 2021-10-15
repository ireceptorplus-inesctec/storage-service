package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.DataProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessingService
{
    @Autowired
    private final DataProcessingRepository dataProcessingRepository;

    public DataProcessingService(DataProcessingRepository dataProcessingRepository)
    {
        this.dataProcessingRepository = dataProcessingRepository;
    }

    public List<DataProcessing> getDataProcessings()
    {
        return new ArrayList<>();
    }
}
