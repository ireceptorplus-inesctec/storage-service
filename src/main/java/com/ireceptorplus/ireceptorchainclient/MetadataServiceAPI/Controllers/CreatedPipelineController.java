package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CreatedPipelineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.CreatedPipelineMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.DataProcessingMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.ScriptMapper;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.CreateAndReadService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.CreatedPipelineService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.DataProcessingService;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services.ScriptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/created_pipeline")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class CreatedPipelineController
{
    @Autowired
    private final ScriptService scriptService;

    @Autowired
    private final ScriptMapper scriptMapper;

    @Autowired
    private final CreatedPipelineService createdPipelineService;

    @Autowired
    private final CreatedPipelineMapper createdPipelineMapper;

    public CreatedPipelineController(ScriptService scriptService, ScriptMapper scriptMapper, CreatedPipelineService createdPipelineService, CreatedPipelineMapper createdPipelineMapper)
    {
        this.scriptService = scriptService;
        this.scriptMapper = scriptMapper;
        this.createdPipelineService = createdPipelineService;
        this.createdPipelineMapper = createdPipelineMapper;
    }

    @Operation(summary = "Creates a new CreatedPipeline object")
    @PostMapping
    public CreatedPipelineDTO create(@Parameter(description = "The new instance of CreatedPipeline to be created") @RequestBody @Valid CreatedPipelineDTO createdPipelineDTO)
    {
        CreatedPipeline createdPipeline = createdPipelineMapper.createdPipelineDTOToCreatedPipeline(createdPipelineDTO);
        CreatedPipeline newCreatedPipeline = createdPipelineService.create(createdPipeline);
        CreatedPipelineDTO newCreatedPipelineDTO = createdPipelineMapper.createdPipelineTocreatedPipelineDTO(newCreatedPipeline);

        return newCreatedPipelineDTO;
    }
}
