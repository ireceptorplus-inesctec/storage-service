package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.DataProcessing;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessingService
{
    public List<DataProcessing> getDataProcessings()
    {
        return new ArrayList<>();
    }
}
