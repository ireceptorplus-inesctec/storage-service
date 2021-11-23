package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ireceptorplus.ireceptorchainclient.FileStorage.StorageService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DatasetService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/dataset")
public class DatasetController
{
    @Autowired
    private DatasetService datasetService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<Dataset> getDatasets()
    {
        return datasetService.readAll();
    }


    @GetMapping("/{id}")
    public Optional<Dataset> getDataset(@PathVariable @NotNull Long id) {
        return datasetService.readById(id);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public DatasetDTO createDataset(@RequestParam("dataset") String datasetStr,
                                    @RequestParam("file") MultipartFile file)
    {
        DatasetDTO datasetDTO = null;
        try
        {
            datasetDTO = objectMapper.readValue(datasetStr, DatasetDTO.class);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return null;
        }
        String originalFileName = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        datasetDTO.setUuid(uuid);
        storageService.store(file, uuid.toString());

        Dataset dataset = modelMapper.map(datasetDTO, Dataset.class);
        dataset.setCreationDate(new Date());
        dataset.setOriginalFileName(originalFileName);

        Dataset createdDataset = datasetService.create(dataset);
        DatasetDTO createdDatasetDTO = modelMapper.map(createdDataset, DatasetDTO.class);

        return createdDatasetDTO;
    }
}
