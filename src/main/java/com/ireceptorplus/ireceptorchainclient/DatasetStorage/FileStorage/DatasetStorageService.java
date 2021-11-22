package com.ireceptorplus.ireceptorchainclient.DatasetStorage.FileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetStorageService extends FileSystemStorageService
{

    @Autowired
    public DatasetStorageService(StorageProperties properties)
    {
        super(properties);
    }
}
