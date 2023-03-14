package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.ToolDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Tool;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
        }
)
public interface ToolMapper
{
    Tool datasetDTOToDataset(ToolDTO germlineDTO);
    ToolDTO datasetDTOToDataset(Tool germline);
}
