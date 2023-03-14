package com.ireceptorplus.storageService.DataTransformationRunning.CommandRunners;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("tools.mixcr")
@Component
public class MixcrProperties
{
    protected String license;

    public MixcrProperties()
    {
    }

    public MixcrProperties(String license)
    {
        this.license = license;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }
}
