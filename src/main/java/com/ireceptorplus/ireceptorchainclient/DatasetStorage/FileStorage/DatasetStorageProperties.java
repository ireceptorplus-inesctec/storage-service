package com.ireceptorplus.ireceptorchainclient.DatasetStorage.FileStorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("file-storage.datasets")
@Component
public class DatasetStorageProperties extends StorageProperties
{
}
