package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions.ErrorParsingJsonObject;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions.UnExistantEntity;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.FileUrlBuilder;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DatasetService;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/dataset")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class DatasetController
{
    @Autowired
    private DatasetService datasetService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DatasetStorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileUrlBuilder fileUrlBuilder;

    /**
     * Returns all Dataset objects stored.
     * @return An array containing all the DataProcessing objects stored.
     */
    @GetMapping
    @Operation(summary = "Returns all Dataset objects stored")
    public List<DatasetDTO> getDatasets()
    {
        List<Dataset> datasets = datasetService.readAll();
        ArrayList<DatasetDTO> datasetDTOs = new ArrayList<>();

        for (Dataset dataset : datasets)
        {
            datasetDTOs.add(modelMapper.map(dataset, DatasetDTO.class));
        }

        return datasetDTOs;
    }

    /**
     * Returns a specific Dataset, with the id received as parameter.
     * @param id The id of the Dataset to be retrieved
     * @return The Dataset with the id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Returns a specific Dataset, with the id received as parameter")
    public DatasetDTO getDataset(@PathVariable @NotNull Long id)
    {
        Dataset dataset = datasetService.readById(id).get();
        if (dataset == null)
            throw new UnExistantEntity("Dataset", id);
        else
            return modelMapper.map(dataset, DatasetDTO.class);
    }

    /**
     * Creates a new Dataset and respective file. Both the metadata of the Dataset and the file are received as parameters on this same request.
     * @param datasetStr A string with the JSON representation of the Dataset object
     * @param file A multipart file that is associated with the Dataset
     * @return The new instance of the Dataset that was just created, contains the id automatically generated by the DB.
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Creates a new Dataset and respective file. Both the metadata of the Dataset and the file are received as parameters on this same request.")
    public DatasetDTO createDataset(
            @Parameter(description = "A string with the JSON representation of the Dataset object")
            @RequestParam("metadata") String datasetStr,
                                    @Parameter(description = "A multipart file that is associated with the Dataset")
                                    @RequestParam("file") MultipartFile file)
    {
        DatasetDTO datasetDTO = null;
        try
        {
            datasetDTO = objectMapper.readValue(datasetStr, DatasetDTO.class);
        } catch (JsonProcessingException e)
        {
            throw new ErrorParsingJsonObject("Error parsing Json object sent.");
        }
        String originalFileName = file.getOriginalFilename();
        String extension = getExtension(originalFileName);
        UUID uuid = UUID.randomUUID();
        datasetDTO.setUuid(uuid);
        storageService.store(file, uuid.toString());

        Dataset dataset = modelMapper.map(datasetDTO, Dataset.class);
        dataset.setCreationDate(new Date());
        dataset.setExtension(extension);
        dataset.setOriginalFileName(originalFileName);
        dataset.setUrl(fileUrlBuilder.buildFromUuid(uuid.toString()));

        Dataset createdDataset = datasetService.create(dataset);
        DatasetDTO createdDatasetDTO = modelMapper.map(createdDataset, DatasetDTO.class);

        return createdDatasetDTO;
    }

    private String getExtension(String originalFileName)
    {
        int extensionIdx = originalFileName.lastIndexOf('.');
        String extension;
        if(extensionIdx > 0)
            extension = originalFileName.substring(extensionIdx + 1);
        else
            extension = null;
        return extension;
    }
}
