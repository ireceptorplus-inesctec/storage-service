package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.ErrorFetchingData;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.ErrorSettingUpConnection;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.TraceabilityDataAwaitingValidation;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

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
    public TraceabilityDataAwaitingValidation getTraceabilityDataAwaitingValidation() throws BlockchainAPIException
    {
        Gateway.Builder builder = setupHyperledgerFabricGatewayBuilder();
        Contract contract = setupContract(builder);

        byte[] result;

        try
        {
            result = contract.evaluateTransaction("getAllAwaitingValidationTraceabilityDataEntries");

            LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully fetched traceability data awaiting validation from the blockchain:");
            LogFactory.getLog(HyperledgerFabricAPI.class).debug(new String(result));

            //TODO parse result to class

        } catch (ContractException e)
        {
            LogFactory.getLog(HyperledgerFabricAPI.class).error("Error fetching data awaiting validation from blockchain");
            e.printStackTrace();
            throw new ErrorFetchingData("Error fetching data awaiting validation from blockchain");
        }

        return null;
    }

    @Override
    public void submitVote(TraceabilityDataAwaitingValidation data, VoteType voteType) throws BlockchainAPIException
    {
        Gateway.Builder builder = setupHyperledgerFabricGatewayBuilder();
        Contract contract = setupContract(builder);

        byte[] result;

        try
        {
            if (voteType == VoteType.YES)
            {
                result = contract.submitTransaction("registerYesVoteForTraceabilityEntryInVotingRound");

                LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully submitted yes vote for traceability data entry awaiting validation: ");
                LogFactory.getLog(HyperledgerFabricAPI.class).debug(new String(result));
            }
            else if (voteType == VoteType.NO)
            {
                result = contract.submitTransaction("registerNoVoteForTraceabilityEntryInVotingRound");

                LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully submitted no vote for traceability data entry awaiting validation: ");
                LogFactory.getLog(HyperledgerFabricAPI.class).debug(new String(result));
            }

            //TODO parse result to class

        } catch (ContractException e)
        {
            LogFactory.getLog(HyperledgerFabricAPI.class).error("Error fetching data awaiting validation from blockchain");
            e.printStackTrace();
            return;
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (TimeoutException e)
        {
            e.printStackTrace();
        }
    }

    private Gateway.Builder setupHyperledgerFabricGatewayBuilder() throws ErrorSettingUpConnection
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
            throw new ErrorSettingUpConnection("Error setting up Hyperledger Fabric connection: opening wallet files failed");
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
            throw new ErrorSettingUpConnection("Error setting up Hyperledger Fabric connection: failed creation of builder");
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

}
