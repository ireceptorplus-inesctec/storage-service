package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageService;
import com.ireceptorplus.ireceptorchainclient.FileStorage.GermlineStorageService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions.UnExistantEntity;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.GermlineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/germline")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class GermlineController
{

    @Autowired
    private final GermlineService germlineService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GermlineStorageService storageService;


    public GermlineController(GermlineService germlineService)
    {
        this.germlineService = germlineService;
    }

    /**
     * Returns all Germline objects stored.
     * @return An array containing all the Germline objects stored.
     */
    @GetMapping
    @Operation(summary = "Returns all Germline objects stored.")
    public List<Germline> getGermlines()
    {
        return germlineService.readAll();
    }

    /**
     * Returns a specific Germline, with the id received as parameter.
     * @param id The id of the Germline to be retrieved
     * @return The Germline with the id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Returns a specific Germline, with the id received as parameter.")
    public Germline getGermline(@PathVariable @NotNull Long id) {
        Germline germline = germlineService.readById(id).get();
        if (germline == null)
            throw new UnExistantEntity("Germline", id);
        else
            return germline;
    }

    /**
     * Creates a new Dataset and respective file. Both the metadata of the Dataset and the file are received as parameters on this same request.
     * @param germlineStr A string with the JSON representation of the Germline object
     * @param file A multipart file that is associated with the Dataset
     * @return The new instance of the Germline that was just created, contains the id automatically generated by the DB.
     */
    @PostMapping
    @Operation(summary = "Creates a new Dataset and respective file. Both the metadata of the Dataset and the file are received as parameters on this same request.")
    public GermlineDTO createGermline(
            @Parameter(description = "A string with the JSON representation of the Germline object")
            @RequestParam("metadata") String germlineStr,
                                      @Parameter(description = "A multipart file that is associated with the Dataset")
                                      @RequestParam("file") MultipartFile file)
    {
        GermlineDTO germlineDTO;
        try
        {
            germlineDTO = objectMapper.readValue(germlineStr, GermlineDTO.class);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return null;
        }

        String originalFileName = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        germlineDTO.setUuid(uuid);
        storageService.store(file, uuid.toString());

        Germline germline = modelMapper.map(germlineDTO, Germline.class);
        germline.setCreationDate(new Date());
        germline.setOriginalFileName(originalFileName);

        Germline createdGermline = germlineService.create(germline);
        GermlineDTO createdGermlineDTO = modelMapper.map(createdGermline, GermlineDTO.class);

        return createdGermlineDTO;
    }
}