package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
@ConfigurationProperties("blockchain-api.hyperledger-fabric.blockchain")
@Component
public class HyperledgerNetworkDetails
{
    protected String networkConfigPath;
    protected String networkName;
    protected String chaincodeId;

    public HyperledgerNetworkDetails()
    {
    }

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

    public void setNetworkConfigPath(String networkConfigPath)
    {
        this.networkConfigPath = networkConfigPath;
    }

    public void setNetworkName(String networkName)
    {
        this.networkName = networkName;
    }

    public void setChaincodeId(String chaincodeId)
    {
        this.chaincodeId = chaincodeId;
    }
}
