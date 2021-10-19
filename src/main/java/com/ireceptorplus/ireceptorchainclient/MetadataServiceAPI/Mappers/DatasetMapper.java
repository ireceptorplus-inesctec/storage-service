package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
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
public interface DatasetMapper
{
    Dataset datasetDTOToDataset(DatasetDTO datasetDTO);
    DatasetDTO datasetDTOToDataset(Dataset dataset);

}
