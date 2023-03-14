package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Germline;
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
