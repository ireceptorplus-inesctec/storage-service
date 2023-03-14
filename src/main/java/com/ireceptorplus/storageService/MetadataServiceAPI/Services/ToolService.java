package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolService extends CreateAndReadService<Tool, Long>
{
    private final ToolRepository toolRepository;

    @Autowired
    public ToolService(ToolRepository toolRepository)
    {
        super(toolRepository);
        this.toolRepository = toolRepository;
    }

    public Tool readByName(String name)
    {
        return toolRepository.findByName(name);
    }
}
