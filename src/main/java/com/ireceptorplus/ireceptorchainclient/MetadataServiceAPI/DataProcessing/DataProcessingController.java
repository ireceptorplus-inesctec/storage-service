package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DataProcessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "api/v1/dataProcessing")
public class DataProcessingController
{
    private final DataProcessingService dataProcessingService;

    @Autowired
    public DataProcessingController(DataProcessingService dataProcessingService)
    {
        this.dataProcessingService = dataProcessingService;
    }

    @GetMapping
    public List<DataProcessing> getDataProcessing()
    {
        return dataProcessingService.getDataProcessings();
    }
}
