package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Script;
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
