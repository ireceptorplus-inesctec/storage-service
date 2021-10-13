package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.GermlineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.GermlineService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("germline")
public class GermlineController
{

    @Autowired
    private final GermlineService germlineService;

    @Autowired
    private ModelMapper modelMapper;


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
    public GermlineDTO createGermline(@RequestBody @Valid GermlineDTO germlineDTO)
    {
        Germline germline = modelMapper.map(germlineDTO, Germline.class);
        Germline createdGermline = germlineService.create(germline);
        GermlineDTO createdGermlineDTO = modelMapper.map(createdGermline, GermlineDTO.class);

        return createdGermlineDTO;
    }
}
