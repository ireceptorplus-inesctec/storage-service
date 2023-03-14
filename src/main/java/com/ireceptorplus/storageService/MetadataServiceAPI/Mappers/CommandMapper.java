package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Command;
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
public interface CommandMapper
{
    @Mappings({
            @Mapping(target="toolName", source="tool.name")
    })
    CommandDTO commandToCommandDTO(Command command);

    @Mappings({
            @Mapping(target="tool", source="toolName")
    })
    Command commandToCommandDTO(CommandDTO command);

}
