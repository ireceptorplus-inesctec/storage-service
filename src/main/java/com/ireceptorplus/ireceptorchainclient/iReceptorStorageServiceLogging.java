package com.ireceptorplus.ireceptorchainclient;

import com.ireceptorplus.ireceptorchainclient.BlockchainAPI.HyperledgerFabricAPI;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class iReceptorStorageServiceLogging
{
    public static void writeLogMessages(Exception e, String message)
    {
        LogFactory.getLog(HyperledgerFabricAPI.class).error(message);
        StringWriter stackTrace = new StringWriter();
        PrintWriter stackTracePw = new PrintWriter(stackTrace);
        e.printStackTrace(stackTracePw);
        LogFactory.getLog(HyperledgerFabricAPI.class).debug(stackTrace);
    }
}
