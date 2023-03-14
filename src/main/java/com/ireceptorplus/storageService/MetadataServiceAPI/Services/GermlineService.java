package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Germline;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.GermlineRepository;
import org.springframework.stereotype.Service;

@Service
public class GermlineService extends CreateAndReadService<Germline, Long>
{
    GermlineRepository germlineRepository;

    public GermlineService(GermlineRepository germlineRepository)
    {
        super(germlineRepository);
        this.germlineRepository = germlineRepository;
    }
}
