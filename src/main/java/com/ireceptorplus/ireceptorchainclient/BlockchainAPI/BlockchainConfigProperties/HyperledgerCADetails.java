package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class contains the hyperledger network configuration details the information related to Certificate Authority (CA), necessary to authenticate the CA.
 */
@ConfigurationProperties("blockchain-api.hyperledger-fabric.ca")
@Component
public class HyperledgerCADetails
{
    protected String caCertPath;
    protected String blockchainPeerUrl;
    protected String caAdminUser;
    protected String caAdminPassword;
    protected String mspId;

    public HyperledgerCADetails()
    {
    }

    public HyperledgerCADetails(String caCertPath, String blockchainPeerUrl, String caAdminUser, String caAdminPassword, String mspId)
    {
        this.caCertPath = caCertPath;
        this.blockchainPeerUrl = blockchainPeerUrl;
        this.caAdminUser = caAdminUser;
        this.caAdminPassword = caAdminPassword;
        this.mspId = mspId;
    }

    public String getCaCertPath()
    {
        return caCertPath;
    }

    public void setCaCertPath(String caCertPath)
    {
        this.caCertPath = caCertPath;
    }

    public String getBlockchainPeerUrl()
    {
        return blockchainPeerUrl;
    }

    public void setBlockchainPeerUrl(String blockchainPeerUrl)
    {
        this.blockchainPeerUrl = blockchainPeerUrl;
    }

    public String getCaAdminUser()
    {
        return caAdminUser;
    }

    public void setCaAdminUser(String caAdminUser)
    {
        this.caAdminUser = caAdminUser;
    }

    public String getCaAdminPassword()
    {
        return caAdminPassword;
    }

    public void setCaAdminPassword(String caAdminPassword)
    {
        this.caAdminPassword = caAdminPassword;
    }

    public String getMspId()
    {
        return mspId;
    }

    public void setMspId(String mspId)
    {
        this.mspId = mspId;
    }
}
