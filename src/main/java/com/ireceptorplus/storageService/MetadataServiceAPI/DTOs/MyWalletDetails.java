package com.ireceptorplus.storageService.MetadataServiceAPI.DTOs;

import org.springframework.beans.factory.annotation.Value;

public class MyWalletDetails
{
    protected String userId;

    private String orgName;

    public MyWalletDetails()
    {
    }

    public MyWalletDetails(String userId, String orgName)
    {
        this.userId = userId;
        this.orgName = orgName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
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
