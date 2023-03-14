package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.GermlineResolver;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.DataProcessing;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                DataProcessingResolver.class,
                GermlineResolver.class,
                CommandMapper.class,
                ProcessingStepMapper.class
        }
)
public interface DataProcessingMapper
{
    @Mappings({
            @Mapping(target="germlineId", source="germline.id")
    })
    DataProcessingDTO dataProcessingtoDataProcessingDTO(DataProcessing dataProcessing);
    @Mappings({
            @Mapping(target="germline", source="germlineId")
    })
    DataProcessing dataProcessingDTOtoDataProcessing(DataProcessingDTO dataProcessingDTO);





}
