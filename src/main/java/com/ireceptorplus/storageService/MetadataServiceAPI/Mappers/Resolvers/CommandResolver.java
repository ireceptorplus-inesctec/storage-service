package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.CommandRepository;
import org.springframework.stereotype.Component;

@Component
public class CommandResolver extends ResolverById<Command>
{
    public CommandResolver(CommandRepository repository)
    {
        super(repository);
    }

    @Override
    public Command getNewEntity()
    {
        return new Command();
    }
}
