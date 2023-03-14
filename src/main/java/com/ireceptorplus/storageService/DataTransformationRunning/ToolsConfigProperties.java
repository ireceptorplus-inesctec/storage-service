package com.ireceptorplus.storageService.DataTransformationRunning;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("tools")
@Component
public class ToolsConfigProperties
{
    protected String mixcrLicense;

    protected String igblastDockerfileLocation;

    public ToolsConfigProperties()
    {
    }

    public String getMixcrLicense()
    {
        return mixcrLicense;
    }

    public void setMixcrLicense(String mixcrLicense)
    {
        this.mixcrLicense = mixcrLicense;
    }

    public String getIgblastDockerfileLocation()
    {
        return igblastDockerfileLocation;
    }

    public void setIgblastDockerfileLocation(String igblastDockerfileLocation)
    {
        this.igblastDockerfileLocation = igblastDockerfileLocation;
    }
}
