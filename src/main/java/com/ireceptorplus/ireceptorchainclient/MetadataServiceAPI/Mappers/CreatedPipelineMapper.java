package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CreatedPipelineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ScriptDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Script;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                ToolMapper.class,
                ToolResolver.class
        }
)
public interface CreatedPipelineMapper
{
    @Mappings({
    })
    CreatedPipelineDTO createdPipelineTocreatedPipelineDTO(CreatedPipeline createdPipeline);

    @Mappings({
    })
    CreatedPipeline createdPipelineDTOToCreatedPipeline(CreatedPipelineDTO createdPipelineDTO);
}