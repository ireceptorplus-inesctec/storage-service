package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.ToolService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tool")
public class ToolController
{
    private final ToolService toolService;

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

}
