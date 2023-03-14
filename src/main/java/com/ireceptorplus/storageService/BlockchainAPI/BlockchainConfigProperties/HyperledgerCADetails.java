package com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class contains the hyperledger network configuration details the information related to Certificate Authority (CA), necessary to authenticate the CA.
 */
@ConfigurationProperties("blockchain-api.hyperledger-fabric.ca")
@Component
public class HyperledgerCADetails
{
    protected String certPath;
    protected String blockchainPeerUrl;
    protected String caAdminUser;
    protected String caAdminPassword;
    protected String mspId;
    protected String affiliation;

    public HyperledgerCADetails()
    {
    }

    public HyperledgerCADetails(String certPath, String blockchainPeerUrl, String caAdminUser, String caAdminPassword, String mspId, String affiliation)
    {
        this.certPath = certPath;
        this.blockchainPeerUrl = blockchainPeerUrl;
        this.caAdminUser = caAdminUser;
        this.caAdminPassword = caAdminPassword;
        this.mspId = mspId;
        this.affiliation = affiliation;
    }

    public String getCertPath()
    {
        return certPath;
    }

    public void setCertPath(String certPath)
    {
        this.certPath = certPath;
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

    public String getAffiliation()
    {
        return affiliation;
    }

    public void setAffiliation(String affiliation)
    {
        this.affiliation = affiliation;
    }
}
