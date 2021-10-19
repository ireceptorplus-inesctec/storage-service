package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                DataProcessingResolver.class
        }
)
public class GermlineMapper
{

}
