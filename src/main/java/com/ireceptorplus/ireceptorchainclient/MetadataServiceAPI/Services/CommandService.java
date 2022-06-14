package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Services;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.CommandRepository;

public class CommandService extends CreateAndReadService<Command, Long>
{
    CommandRepository commandRepository;

    protected CommandService(CommandRepository commandRepository)
    {
        super(commandRepository);
        this.commandRepository = commandRepository;
    }
}
