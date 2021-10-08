package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToolService
{
    @Autowired
    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository)
    {
        this.toolRepository = toolRepository;
    }

    public List<Tool> findAll()
    {
        return toolRepository.findAll();
    }

    public Optional<Tool> findById(Long id)
    {
        return toolRepository.findById(id);
    }
}
