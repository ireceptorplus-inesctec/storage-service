package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.PipelineDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.DataProcessingResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                ToolResolver.class,
        }
)
public interface TraceabilityDataMapper
{
    @Mappings({
            @Mapping(target="hashValue", source="sha256Checksum")
    })
    DownloadbleFile datasetToDownloadableFile(Dataset dataset);

    @Mappings({
            @Mapping(target="toolId", source="toolName")
    })
    Command commandDTOToBlockchainCommand(CommandDTO commandDTO);

    default String datasetUuidToString(UUID uuid)
    {
        return uuid.toString();
    }

}
