package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.GermlineResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.GermlineRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                DataProcessingResolver.class,
                GermlineResolver.class
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
