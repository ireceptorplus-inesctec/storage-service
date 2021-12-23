package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.DataProcessingRepository;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DataProcessingResolver extends ResolverById<DataProcessing>
{
    @Autowired
    public DataProcessingResolver(JpaRepository<DataProcessing, Long> repository)
    {
        super(repository);
    }

    @Override
    public DataProcessing getNewEntity()
    {
        return new DataProcessing();
    }
}
