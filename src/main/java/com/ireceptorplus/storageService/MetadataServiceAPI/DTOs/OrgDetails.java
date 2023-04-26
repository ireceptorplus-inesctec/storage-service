package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;
public class OrgDetails
{
    String orgName;

    public OrgDetails()
    {
    }

    public OrgDetails(String orgName)
    {
        this.orgName = orgName;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
}
