package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ireceptorplus.ireceptorchainclient.FileStorage.DatasetStorageService;
import com.ireceptorplus.ireceptorchainclient.FileStorage.GermlineStorageService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.GermlineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.sun.istack.NotNull;
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

    @GetMapping
    public List<Germline> getGermlines()
    {
        return germlineService.readAll();
    }


    @GetMapping("/{id}")
    public Optional<Germline> getGermline(@PathVariable @NotNull Long id) {
        return germlineService.readById(id);
    }

    @PostMapping
    public GermlineDTO createGermline(@RequestParam("metadata") String germlineStr,
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
