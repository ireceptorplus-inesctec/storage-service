package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DTOs.TraceabilityData.TraceabilityDataAwaitingValidation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class HyperledgerFabricAPI implements BlockchainAPI
{
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    protected HyperledgerNetworkDetails hyperledgerNetworkDetails;
    protected HyperledgerWalletDetails hyperledgerWalletDetails;

    public HyperledgerFabricAPI(HyperledgerNetworkDetails hyperledgerNetworkDetails, HyperledgerWalletDetails hyperledgerWalletDetails)
    {
        this.hyperledgerNetworkDetails = hyperledgerNetworkDetails;
        this.hyperledgerWalletDetails = hyperledgerWalletDetails;
    }

    @Override
    public TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidation()
    {
        Gateway.Builder builder = setupHyperledgerFabricGatewayBuilder();
        Contract contract = setupContract(builder);

        byte[] result;

        try
        {
            result = contract.evaluateTransaction("getAllAwaitingValidationTraceabilityDataEntries");

            System.out.println("Result from blockchain:");
            System.out.println(new String(result));

            //TODO parse result to class

        } catch (ContractException e)
        {
            LogFactory.getLog(HyperledgerFabricAPI.class).error("Error fetching data awaiting validation from blockchain");
            e.printStackTrace();
        }

        return null;
    }

    private Gateway.Builder setupHyperledgerFabricGatewayBuilder()
    {
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get(this.hyperledgerWalletDetails.walletPath);
        Wallet wallet = null;
        try
        {
            wallet = Wallets.newFileSystemWallet(walletPath);
        } catch (IOException e)
        {
            LogFactory.getLog(HyperledgerFabricAPI.class).error("Error setting up Hyperledger Fabric connection: opening wallet files failed");
            e.printStackTrace();
        }
        // load a CCP
        Path networkConfigPath = Paths.get(this.hyperledgerNetworkDetails.networkConfigPath);

        Gateway.Builder builder = Gateway.createBuilder();
        try
        {
            builder.identity(wallet, hyperledgerWalletDetails.userId).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e)
        {
            LogFactory.getLog(HyperledgerFabricAPI.class).error("Error setting up Hyperledger Fabric connection: failed creation of builder");
            e.printStackTrace();
        }
        return builder;
    }

    private Contract setupContract(Gateway.Builder builder)
    {
        Gateway gateway = builder.connect();
        // get the network and contract
        Network network = gateway.getNetwork(hyperledgerNetworkDetails.networkName);
        Contract contract = network.getContract(hyperledgerNetworkDetails.chaincodeId);

        return contract;
    }

    @Override
    public void submitVote(TraceabilityDataAwaitingValidation data)
    {

    }

}
