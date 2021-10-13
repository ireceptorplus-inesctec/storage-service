package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.GermlineRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public class GermlineService extends CreateAndReadService<Germline, Long>
{
    GermlineRepository germlineRepository;

    public GermlineService(GermlineRepository germlineRepository)
    {
        super(germlineRepository);
        this.germlineRepository = germlineRepository;
    }
}
