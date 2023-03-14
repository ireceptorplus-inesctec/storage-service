package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers;

import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.Command;
import com.ireceptorplus.storageService.BlockchainAPI.DataClasses.ReproducibilityData.DownloadbleFile;
import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.DatasetDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers.ToolResolver;
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
    DownloadbleFile datasetToDownloadableFile(DatasetDTO dataset);

    @Mappings({
            @Mapping(target="toolId", source="toolName")
    })
    Command commandDTOToBlockchainCommand(CommandDTO commandDTO);

    default String datasetUuidToString(UUID uuid)
    {
        return uuid.toString();
    }

}
