package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.DataProcessing;
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
