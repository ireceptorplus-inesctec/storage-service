package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.ProcessingStepDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.ProcessingStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface DataProcessingMapper
{
    @Mappings({
            @Mapping(target="germlineId", source="germline.id")
    })
    DataProcessingDTO dataProcessingtoDataProcessingDTO(DataProcessing dataProcessing);
    @Mappings({
            @Mapping(target="germline.id", source="germlineId")
    })
    DataProcessing dataProcessingDTOtoDataProcessing(DataProcessingDTO dataProcessingDTO);

    List<ProcessingStepDTO> processingStepToProcessingStepDTOs(List<ProcessingStep> processingSteps);
    List<ProcessingStep> processingStepDTOToProcessingSteps(List<ProcessingStepDTO> processingSteps);

    ProcessingStepDTO processingStepToProcessingStepDTO(ProcessingStep processingStep);
    ProcessingStep processingStepDTOToProcessingStep(ProcessingStepDTO processingStep);

    CommandDTO commandToCommandDTO(Command command);
    Command commandToCommandDTO(CommandDTO command);
}
