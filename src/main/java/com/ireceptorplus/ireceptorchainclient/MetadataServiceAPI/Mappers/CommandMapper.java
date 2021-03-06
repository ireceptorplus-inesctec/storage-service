package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import org.mapstruct.*;

import java.util.List;

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
