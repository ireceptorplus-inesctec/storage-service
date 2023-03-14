package com.ireceptorplus.storageService.FileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GermlineStorageService extends FileSystemStorageService
{
    @Autowired
    public GermlineStorageService(GermlineStorageProperties properties)
    {
        super(properties);
    }
}
