package com.ireceptorplus.storageService.MetadataServiceAPI.Mappers.Resolvers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.DTOWithId;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ResolverById<T>
{
    protected JpaRepository<T, Long> repository;

    public ResolverById(JpaRepository<T, Long> repository)
    {
        this.repository = repository;
    }

    @ObjectFactory
    public T resolve(DTOWithId dto, @TargetType Class<T> type)
    {
        T resolved = null;
        if (dto != null && dto.getId() != null)
        {
            resolved = repository.findById(dto.getId()).get();
        } else
        {
            resolved = getNewEntity();
        }

        return resolved;
    }

    public T resolveById(Long id)
    {
        T resolved = repository.findById(id).get();

        return resolved;
    }

    public abstract T getNewEntity();
}
