package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.DataProcessingRepository;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class DataProcessingResolver
{
    @Autowired
    private DataProcessingRepository repository;

    @ObjectFactory
    public DataProcessing resolve(DataProcessingDTO dto, @TargetType Class<DataProcessing> type)
    {
        DataProcessing resolved = null;
        if (dto != null && dto.getId() != null)
        {
            resolved = repository.findById(dto.getId()).get();
        } else
        {
            resolved = new DataProcessing();
        }

        return resolved;
    }
}
