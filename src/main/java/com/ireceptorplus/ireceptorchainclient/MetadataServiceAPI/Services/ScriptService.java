package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Script;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.ScriptRepository;
import org.springframework.stereotype.Service;

@Service
public class ScriptService extends CreateAndReadService<Script, Long>
{
    ScriptRepository scriptRepository;

    public ScriptService(ScriptRepository scriptRepository)
    {
        super(scriptRepository);
        this.scriptRepository = scriptRepository;
    }
}
