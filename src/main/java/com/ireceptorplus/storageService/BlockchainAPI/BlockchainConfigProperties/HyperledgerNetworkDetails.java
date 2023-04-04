package com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class contains the hyperledger network configuration details the information related to the blockchain, necessary to contact and authenticate the blockchain network.
 */
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
