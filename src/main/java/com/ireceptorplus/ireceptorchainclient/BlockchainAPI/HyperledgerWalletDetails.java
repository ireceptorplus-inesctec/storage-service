package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

public class HyperledgerWalletDetails
{
    String walletPath;
    String userId;

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
