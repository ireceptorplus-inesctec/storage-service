package com.ireceptorplus.storageService.BlockchainAPI;

import com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties.HyperledgerCADetails;
import com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties.HyperledgerNetworkConfig;
import com.ireceptorplus.storageService.BlockchainAPI.BlockchainConfigProperties.HyperledgerWalletDetails;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;


public  class ProductionNetworkHyperledgerFabricAPI extends HyperledgerFabricAPITest
{

    @Autowired
    public ProductionNetworkHyperledgerFabricAPI(HyperledgerNetworkConfig hyperledgerNetworkConfig, HyperledgerWalletDetails hyperledgerWalletDetails, HyperledgerCADetails hyperledgerCADetails)
    {
        super(hyperledgerNetworkConfig, hyperledgerWalletDetails, hyperledgerCADetails);
    }

    public void clientApp() throws IOException, ContractException, InterruptedException, TimeoutException
    {
        String userId = hyperledgerWalletDetails.getUserId();
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get(hyperledgerWalletDetails.getPath());
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get(hyperledgerNetworkConfig.getNetworkConfigPath());

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userId).networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("ireceptorchain");

            byte[] result;

            result = contract.evaluateTransaction("queryAllCars");
            System.out.println(new String(result));

            contract.submitTransaction("createCar", "CAR10", "VW", "Polo", "Grey", "Mary");

            result = contract.evaluateTransaction("queryCar", "CAR10");
            System.out.println(new String(result));

            contract.submitTransaction("changeCarOwner", "CAR10", "Archie");

            result = contract.evaluateTransaction("queryCar", "CAR10");
            System.out.println(new String(result));
        }
    }
}
