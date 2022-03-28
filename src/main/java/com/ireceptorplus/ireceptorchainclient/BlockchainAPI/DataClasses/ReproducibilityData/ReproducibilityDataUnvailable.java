package com.ireceptorplus.ireceptorchainclient.BlockchainAPI.DataClasses.ReproducibilityData;

import java.util.ArrayList;

public class ReproducibilityDataUnvailable extends ReproducibilityData
{
    public ReproducibilityDataUnvailable()
    {
        super(new ArrayList<>(), new ReproducibleScriptUnavailable(), new ArrayList<>());
    }
}
