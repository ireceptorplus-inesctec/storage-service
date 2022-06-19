package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.CommandRepository;
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
