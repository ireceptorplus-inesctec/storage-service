package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("blockchain-api.hyperledger-fabric.wallet")
@Component
public class HyperledgerWalletDetails
{
    protected String path;
    protected String userId;

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
}
