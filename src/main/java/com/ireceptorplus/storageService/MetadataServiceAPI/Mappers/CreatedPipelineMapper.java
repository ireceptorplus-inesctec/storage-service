package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.CreatedPipelineDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.CommandResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipeline;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                ToolMapper.class,
                ToolResolver.class,
                CommandMapper.class,
                CommandResolver.class,
        }
)
public interface CreatedPipelineMapper
{
    @Mappings({
            @Mapping(target="commandId", source="command.id")
    })
    CreatedPipelineDTO createdPipelineTocreatedPipelineDTO(CreatedPipeline createdPipeline);

    @Mappings({
            @Mapping(target="command", source="commandId")
    })
    CreatedPipeline createdPipelineDTOToCreatedPipeline(CreatedPipelineDTO createdPipelineDTO);
}