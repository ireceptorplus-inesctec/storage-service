package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import java.nio.file.Path;

public class HyperledgerNetworkDetails
{
    String networkConfigPath;
    String networkName;
    String chaincodeId;

    public HyperledgerNetworkDetails(String networkConfigPath, String networkName, String chaincodeId)
    {
        this.networkConfigPath = networkConfigPath;
        this.networkName = networkName;
        this.chaincodeId = chaincodeId;
    }

    public String getNetworkConfigPath()
    {
        return networkConfigPath;
    }

    public String getNetworkName()
    {
        return networkName;
    }

    public String getChaincodeId()
    {
        return chaincodeId;
    }
}
