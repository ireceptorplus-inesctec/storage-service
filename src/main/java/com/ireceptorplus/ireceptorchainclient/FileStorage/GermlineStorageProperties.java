package com.ireceptorplus.ireceptorchainclient.FileStorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("file-storage.germline")
@Component
public class GermlineStorageProperties extends StorageProperties
{
}
