package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Script;
import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ScriptResolver extends ResolverById<Script>
{
    public ScriptResolver(JpaRepository<Script, Long> repository)
    {
        super(repository);
    }

    @Override
    public Script getNewEntity()
    {
        return new Script();
    }
}
