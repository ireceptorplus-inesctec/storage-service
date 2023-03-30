package com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class contains the hyperledger network configuration details the information related to the blockchain, necessary to contact and authenticate the blockchain network.
 */
public class HyperledgerNetworkDetails
{
    protected HyperledgerNetworkConfig networkConfig;

    public HyperledgerNetworkDetails(HyperledgerNetworkConfig networkConfig)
    {
        this.networkConfig = networkConfig;

    }

    public void parseNetworkConfigFile()
    {
        //TODO
        return;
    }


}
