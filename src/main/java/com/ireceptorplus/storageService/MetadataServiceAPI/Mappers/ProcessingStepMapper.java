package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.ProcessingStep;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                CommandMapper.class
        }
)
public interface ProcessingStepMapper
{

    @Mappings({
    })
    ProcessingStepDTO processingStepToProcessingStepDTO(ProcessingStep processingStep);
    @Mappings({
            @Mapping(target = "dataProcessing", ignore = true)
    })
    ProcessingStep processingStepDTOToProcessingStep(ProcessingStepDTO processingStep);

    List<ProcessingStepDTO> processingStepToProcessingStepDTOs(List<ProcessingStep> processingSteps);
    List<ProcessingStep> processingStepDTOToProcessingSteps(List<ProcessingStepDTO> processingSteps);
    //aqui ter uma funçao que adicione o id

}
