package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

public class DatasetResolver extends ResolverById<Dataset>
{
    public DatasetResolver(JpaRepository<Dataset, Long> repository)
    {
        super(repository);
    }

    @Override
    public Dataset getNewEntity()
    {
        return new Dataset();
    }
}
