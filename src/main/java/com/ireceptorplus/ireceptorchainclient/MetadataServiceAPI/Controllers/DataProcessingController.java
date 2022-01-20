package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.DataProcessingMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DataProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/data_processing")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
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

    /**
     * Returns all DataProcessing objects stored.
     * @return An array containing all the DataProcessing objects stored.
     */
    @Operation(summary = "Returns all DataProcessing objects stored.")
    @GetMapping
    public List<DataProcessing> getDataProcessing()
    {
        return dataProcessingService.getDataProcessings();
    }

    /**
     * Creates a new DataProcessing object.
     * @param dataProcessingDTO The new instance of DataProcessing to be created
     * @return The new instance of the DataProcessing that was just created, contains the id automatically generated by the DB.
     */
    @Operation(summary = "Creates a new DataProcessing object")
    @PostMapping
    public DataProcessingDTO create(@Parameter(description = "The new instance of DataProcessing to be created") @RequestBody @Valid DataProcessingDTO dataProcessingDTO)
    {
        DataProcessing dataProcessing = dataProcessingMapper.dataProcessingDTOtoDataProcessing(dataProcessingDTO);
        DataProcessing createdDataProcessing = dataProcessingService.create(dataProcessing);
        DataProcessingDTO createdDataProcessingDTO = dataProcessingMapper.dataProcessingtoDataProcessingDTO(createdDataProcessing);

        return createdDataProcessingDTO;
    }

}