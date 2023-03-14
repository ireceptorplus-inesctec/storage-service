package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.GermlineDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.GermlineResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Germline;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                GermlineResolver.class
        }
)
public interface GermlineMapper
{
        Germline datasetDTOToDataset(GermlineDTO germlineDTO);
        GermlineDTO datasetDTOToDataset(Germline germline);


}
