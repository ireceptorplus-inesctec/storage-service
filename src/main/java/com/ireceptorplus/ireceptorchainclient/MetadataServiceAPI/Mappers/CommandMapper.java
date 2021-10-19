package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
        }
)
public interface CommandMapper
{
    @Mappings({
            @Mapping(target="toolId", source="tool.id")
    })
    CommandDTO commandToCommandDTO(Command command);

    @Mappings({
            @Mapping(target="tool", source="toolId")
    })
    Command commandToCommandDTO(CommandDTO command);

}
