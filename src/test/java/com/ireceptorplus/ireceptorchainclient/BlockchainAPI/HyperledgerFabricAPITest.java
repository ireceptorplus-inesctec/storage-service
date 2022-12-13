package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainConfigProperties.HyperledgerNetworkDetails;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainConfigProperties.HyperledgerWalletDetails;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HyperledgerFabricAPITest extends HyperledgerFabricAPI
{
    protected String caCertPath;
    protected String blockchainPeerUrl;

    public HyperledgerFabricAPITest(HyperledgerNetworkDetails hyperledgerNetworkDetails,
                                    HyperledgerWalletDetails hyperledgerWalletDetails,
                                    String caCertPath, String blockchainPeerUrl)
    {
        super(hyperledgerNetworkDetails, hyperledgerWalletDetails);
        this.caCertPath = caCertPath;
        this.blockchainPeerUrl = blockchainPeerUrl;
    }
}
