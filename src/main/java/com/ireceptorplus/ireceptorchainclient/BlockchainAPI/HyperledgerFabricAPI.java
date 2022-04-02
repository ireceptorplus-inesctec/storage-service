package com.ireceptorplus.ireceptorchainclient.BlockchainAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.VoteResultReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ProcessingDetails;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.*;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class HyperledgerFabricAPI implements BlockchainAPI
{
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    protected HyperledgerNetworkDetails hyperledgerNetworkDetails;
    protected HyperledgerWalletDetails hyperledgerWalletDetails;
    private ProcessingDetails processingDetails;

    public HyperledgerFabricAPI(HyperledgerNetworkDetails hyperledgerNetworkDetails, HyperledgerWalletDetails hyperledgerWalletDetails)
    {
        this.hyperledgerNetworkDetails = hyperledgerNetworkDetails;
        this.hyperledgerWalletDetails = hyperledgerWalletDetails;
    }

    @Override
    public TraceabilityDataReturnType createTraceabilityDataEntry(TraceabilityDataToBeSubmitted data) throws BlockchainAPIException
    {
        Gateway.Builder builder = setupHyperledgerFabricGatewayBuilder();
        Contract contract = setupContract(builder);

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String dataJson = objectMapper.writeValueAsString(data);

            UUID uuid = UUID.randomUUID();
            processingDetails = data.getProcessingDetails();
            //byte[] result = contract.submitTransaction("createTraceabilityDataEntryByObject", uuid.toString(), data.getInputDatasetHashValue(), data.getOutputDatasetHashValue(), processingDetails.getSoftwareId(), processingDetails.getSoftwareVersion(), processingDetails.getSoftwareBinaryExecutableHashValue(), processingDetails.getSoftwareConfigParams(), data.getValue().toString());
            byte[] result = contract.submitTransaction("createTraceabilityDataEntryByObject", uuid.toString(), dataJson);
            String resultStr = new String(result);

            LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully created traceability data: ");
            LogFactory.getLog(HyperledgerFabricAPI.class).debug(resultStr);

            TraceabilityDataReturnType resultTraceabilityData = objectMapper.readValue(resultStr, TraceabilityDataReturnType.class);

            return resultTraceabilityData;
        } catch (ContractException | InterruptedException | TimeoutException e)
        {
            String message = "Error creating traceability data: blockchain returned " + e.getMessage();
            writeLogMessages(e, message);
            throw new ErrorSubmittingData(message);
        } catch (JsonProcessingException e)
        {
            String message = "Error creating JSON object that represents traceability data to be created.";
            writeLogMessages(e, message);
            throw new ErrorSubmittingData(message);
        }
    }

    @Override
    public List<TraceabilityDataReturnType> getTraceabilityDataAwaitingValidation() throws BlockchainAPIException
    {
        Gateway.Builder builder = setupHyperledgerFabricGatewayBuilder();
        Contract contract = setupContract(builder);

        try
        {
            byte[] result = contract.evaluateTransaction("getAllAwaitingValidationTraceabilityDataEntries");
            String resultStr = new String(result);

            LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully fetched traceability data awaiting validation from the blockchain:");
            LogFactory.getLog(HyperledgerFabricAPI.class).debug(resultStr);

            ObjectMapper objectMapper = new ObjectMapper();
            TraceabilityDataReturnType[] dataArray = objectMapper.readValue(resultStr, TraceabilityDataReturnType[].class);
            List<TraceabilityDataReturnType> dataList = Arrays.asList(dataArray);

            return dataList;
        } catch (ContractException e)
        {
            String message = "Error fetching data awaiting validation from blockchain";
            writeLogMessages(e, message);
            throw new ErrorFetchingData(message);
        } catch (JsonProcessingException e)
        {
            String message = "Error fetching data awaiting validation from blockchain: error parsing result from blockchain";
            writeLogMessages(e, message);
            throw new ErrorFetchingData(message);
        }
    }

    @Override
    public VoteResultReturnType submitVote(TraceabilityDataReturnType data, VoteType voteType) throws BlockchainAPIException
    {
        Gateway.Builder builder = setupHyperledgerFabricGatewayBuilder();
        Contract contract = setupContract(builder);

        try
        {
            String resultStr;
            if (voteType == VoteType.YES)
            {
                byte[] result = contract.submitTransaction("registerYesVoteForTraceabilityEntryInVotingRound", data.getUuid());
                resultStr = new String(result);

                LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully submitted yes vote for traceability data entry awaiting validation: ");
            }
            else if (voteType == VoteType.NO)
            {
                byte[] result = contract.submitTransaction("registerNoVoteForTraceabilityEntryInVotingRound");
                resultStr = new String(result);

                LogFactory.getLog(HyperledgerFabricAPI.class).debug("Successfully submitted no vote for traceability data entry awaiting validation: ");
            }
            else
            {
                LogFactory.getLog(HyperledgerFabricAPI.class).debug("Unrecognized vote option: " + voteType);
                throw new UnrecognizedVoteType("Unrecognized vote option: " + voteType);
            }

            LogFactory.getLog(HyperledgerFabricAPI.class).debug(resultStr);

            ObjectMapper objectMapper = new ObjectMapper();
            VoteResultReturnType voteResult = objectMapper.readValue(resultStr, VoteResultReturnType.class);

            return voteResult;
        } catch (ContractException | InterruptedException | TimeoutException e)
        {
            String message = "Error submitting " + voteType + " vote for traceability data " + data + ". Blockchain returned: " + e.getMessage();
            writeLogMessages(e, message);
            throw new ErrorSubmittingVote(message);
        } catch (JsonProcessingException e)
        {
            String message = "Error submitting " + voteType + " vote for traceability data " + data + ". Blockchain returned: " + e.getMessage();
            writeLogMessages(e, message);
            throw new ErrorSubmittingVote(message);
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

    private void writeLogMessages(Exception e, String message) {
        LogFactory.getLog(HyperledgerFabricAPI.class).error(message);
        StringWriter stackTrace = new StringWriter();
        PrintWriter stackTracePw = new PrintWriter(stackTrace);
        e.printStackTrace(stackTracePw);
        LogFactory.getLog(HyperledgerFabricAPI.class).debug(stackTrace);
    }


}
