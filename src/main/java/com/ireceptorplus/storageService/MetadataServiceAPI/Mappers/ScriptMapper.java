package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.ScriptDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Script;
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
public interface ScriptMapper
{
    ScriptDTO scriptToScriptDTO(Script script);

    Script scriptToScriptDTO(ScriptDTO command);
}
