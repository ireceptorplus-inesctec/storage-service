package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ToolDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.ToolService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tool")
public class ToolController
{
    private final ToolService toolService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ToolController(ToolService toolService)
    {
        this.toolService = toolService;
    }

    @GetMapping
    public List<Tool> getTools()
    {
        return toolService.findAll();
    }


    @GetMapping("/{id}")
    public Optional<Tool> getTool(@PathVariable @NotNull Long id) {
        return toolService.findById(id);
    }

    @PostMapping
    public ToolDTO createTool(@RequestBody @Valid ToolDTO toolDTO)
    {
        Tool tool = modelMapper.map(toolDTO, Tool.class);
        Tool createdTool = toolService.create(tool);
        ToolDTO createdToolDTO = modelMapper.map(createdTool, ToolDTO.class);

        return createdToolDTO;
    }

}
