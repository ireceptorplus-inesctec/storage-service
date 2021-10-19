package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.DataProcessingDTO;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.DataProcessingRepository;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GermlineResolver extends ResolverById<Germline>
{
    @Autowired
    public GermlineResolver(JpaRepository<Germline, Long> repository)
    {
        super(repository);
    }

    @Override
    public Germline getNewEntity()
    {
        return new Germline();
    }


}
