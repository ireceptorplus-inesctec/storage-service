package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ConfigurationProperties("file-storage")
@Service
public class FileUrlBuilder
{
    private static final String defaultListeningPort = "8080";

    private String apiUrl;

    public String buildFromUuid(String uuid)
    {
        return apiUrl + "/file/" + uuid;
    }

    public String getApiUrl()
    {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl)
    {
        this.apiUrl = apiUrl;
    }
}
