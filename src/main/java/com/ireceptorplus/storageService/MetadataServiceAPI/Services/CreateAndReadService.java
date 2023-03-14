package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class CreateAndReadService<T, ID>
{
    private final JpaRepository<T, ID> repository;

    protected CreateAndReadService(JpaRepository<T, ID> repository)
    {
        this.repository = repository;
    }

    public T create(T tool)
    {
        return repository.save(tool);
    }

    public List<T> readAll()
    {
        return repository.findAll();
    }

    public Optional<T> readById(ID id)
    {
        return repository.findById(id);
    }
}
