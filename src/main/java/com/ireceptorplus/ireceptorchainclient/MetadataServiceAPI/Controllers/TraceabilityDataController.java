package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Controllers;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.BlockchainAPI;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeInputDataTypes.TraceabilityDataToBeSubmitted;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.TraceabilityDataReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.ChaincodeReturnDataTypes.VoteResultReturnType;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.Exceptions.BlockchainAPIException;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.HyperledgerFabricAPI;
import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.VoteType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/traceability_data")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class TraceabilityDataController
{
    HyperledgerFabricAPI blockchainAPI;

    @Autowired
    public TraceabilityDataController(HyperledgerFabricAPI blockchainAPI)
    {
        this.blockchainAPI = blockchainAPI;
    }

    @Operation(summary = "Returns all traceability data entries awaiting validation on the blockchain.")
    @PostMapping("/all")
    public TraceabilityDataReturnType createTraceabilityDataEntry(TraceabilityDataToBeSubmitted data) throws BlockchainAPIException
    {
        try
        {
            return blockchainAPI.createTraceabilityDataEntry(data);
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    @Operation(summary = "Returns all traceability data entries awaiting validation on the blockchain.")
    @GetMapping()
    public List<TraceabilityDataReturnType> getAllTraceabilityDataAwaitingValidationFromBlokchain() throws BlockchainAPIException
    {
        try
        {
            return blockchainAPI.getTraceabilityDataAwaitingValidation();
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    @Operation(summary = "Submits a vote to the traceability data entry with the uuid received as parameter.")
    @Parameter(name = "dataUuid", description = "The uuid of the traceability data entry to vote for")
    @Parameter(name = "voteType", description = "A string representing the vote type: can be either \"YES\" or \"NO\"")
    @PostMapping("/submit_vote")
    VoteResultReturnType submitVote(String dataUuid, String voteType) throws BlockchainAPIException
    {
        VoteType voteType_ = voteType.toUpperCase(Locale.ROOT).equals("YES") ? VoteType.YES : VoteType.NO;
        try
        {
            return blockchainAPI.submitVote(dataUuid, voteType_);
        } catch (BlockchainAPIException e)
        {
            handleBlockchainAPIException(e);
            throw e;
        }
    }

    private void handleBlockchainAPIException(BlockchainAPIException e) throws BlockchainAPIException
    {
        LogFactory.getLog(TraceabilityDataController.class).error(e.getMessage());
        StringWriter stackTrace = new StringWriter();
        PrintWriter stackTracePw = new PrintWriter(stackTrace);
        e.printStackTrace(stackTracePw);
        LogFactory.getLog(TraceabilityDataController.class).debug(stackTrace);
    }

}
