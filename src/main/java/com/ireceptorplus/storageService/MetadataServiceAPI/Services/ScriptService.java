package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Script;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.ScriptRepository;
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
