package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DatasetService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public List<Dataset> getDatasets()
    {
        return datasetService.readAll();
    }


    @GetMapping("/{id}")
    public Optional<Dataset> getDataset(@PathVariable @NotNull Long id) {
        return datasetService.readById(id);
    }

    @PostMapping
    public DatasetDTO createDataset(@RequestBody @Valid DatasetDTO toolDTO)
    {
        Dataset tool = modelMapper.map(toolDTO, Dataset.class);
        Dataset createdDataset = datasetService.create(tool);
        DatasetDTO createdDatasetDTO = modelMapper.map(createdDataset, DatasetDTO.class);

        return createdDatasetDTO;
    }
}
