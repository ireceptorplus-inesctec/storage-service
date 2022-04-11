package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("blockchain-api.hyperledger-fabric.wallet")
@Component
public class HyperledgerWalletDetails
{
    protected String walletPath;
    protected String userId;

    public HyperledgerWalletDetails()
    {
    }

    public HyperledgerWalletDetails(String walletPath, String userId)
    {
        this.walletPath = walletPath;
        this.userId = userId;
    }

    public String getWalletPath()
    {
        return walletPath;
    }

    public String getUserId()
    {
        return userId;
    }
}
