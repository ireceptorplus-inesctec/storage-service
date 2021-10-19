package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.DataProcessing;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
