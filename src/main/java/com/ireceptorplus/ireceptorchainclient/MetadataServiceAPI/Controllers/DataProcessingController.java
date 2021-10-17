package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.DataProcessingMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DataProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("data_processing")
public class DataProcessingController
{
    @Autowired
    private final DataProcessingService dataProcessingService;

    @Autowired
    private final DataProcessingMapper dataProcessingMapper;

    public DataProcessingController(DataProcessingService dataProcessingService, DataProcessingMapper dataProcessingMapper)
    {
        this.dataProcessingService = dataProcessingService;
        this.dataProcessingMapper = dataProcessingMapper;
    }


    @GetMapping
    public List<DataProcessing> getDataProcessing()
    {
        return dataProcessingService.getDataProcessings();
    }

    @PostMapping
    public DataProcessingDTO create(@RequestBody @Valid DataProcessingDTO dataProcessingDTO)
    {
        DataProcessing dataProcessing = dataProcessingMapper.dataProcessingDTOtoDataProcessing(dataProcessingDTO);
        DataProcessing createdDataProcessing = dataProcessingService.create(dataProcessing);
        DataProcessingDTO createdDataProcessingDTO = dataProcessingMapper.dataProcessingtoDataProcessingDTO(createdDataProcessing);

        return createdDataProcessingDTO;
    }

}
