package com.ireceptorplus.storageService.FileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetStorageService extends FileSystemStorageService
{

    @Autowired
    public DatasetStorageService(DatasetStorageProperties properties)
    {
        super(properties);
    }
}
