package com.ireceptorplus.storageService.DataTransformationRunning.SoftwareRequirements;

public class SoftwareRequirement
{
    String name;
    String websiteUrl;

    public SoftwareRequirement(String name, String websiteUrl)
    {
        this.name = name;
        this.websiteUrl = websiteUrl;
    }

    public String getName()
    {
        return name;
    }

    public String getWebsiteUrl()
    {
        return websiteUrl;
    }
}
