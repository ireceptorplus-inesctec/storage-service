package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.CommandRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandService extends CreateAndReadService<Command, Long>
{
    CommandRepository commandRepository;

    protected CommandService(CommandRepository commandRepository)
    {
        super(commandRepository);
        this.commandRepository = commandRepository;
    }
}
