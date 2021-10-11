package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ToolDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DatasetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("dataset")
public class DatasetController
{
    @Autowired
    private final DatasetService datasetService;

    @Autowired
    private ModelMapper modelMapper;

    public DatasetController(DatasetService datasetService)
    {
        this.datasetService = datasetService;
    }

    public DatasetDTO create(@RequestBody @Valid DatasetDTO datasetDTO)
    {
        Dataset dataset = modelMapper.map(datasetDTO, Dataset.class);
        Dataset createdTool = datasetService.create(dataset);
        DatasetDTO createdToolDTO = modelMapper.map(createdTool, DatasetDTO.class);

        return createdToolDTO;
    }
}
