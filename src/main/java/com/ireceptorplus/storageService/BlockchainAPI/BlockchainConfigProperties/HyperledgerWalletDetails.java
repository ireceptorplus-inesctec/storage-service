package com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class contains the hyperledger fabric wallet configuration details the information related to the peer, necessary to authenticate the peer in the blockchain network.
 */
@ConfigurationProperties("blockchain-api.hyperledger-fabric.wallet")
@Component
public class HyperledgerWalletDetails
{
    protected String path;
    protected String userId;

    @Value("${ORG_NAME}")
    private String orgName;

    public HyperledgerWalletDetails()
    {
    }

    public HyperledgerWalletDetails(String path, String userId)
    {
        this.path = path;
        this.userId = userId;
    }

    public String getPath()
    {
        return path;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setPath(String path)
    {
        this.path = path;
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
