package com.ireceptorplus.ireceptorchainclient.DataTransformationRunning;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("tools")
@Component
public class ToolsConfigProperties
{
    protected String mixcrLicense;

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
}
