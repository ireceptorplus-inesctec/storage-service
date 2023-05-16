package com.ireceptorplus.storageService.MetadataServiceAPI.Services;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipeline;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.CreatedPipelineState;
import com.ireceptorplus.storageService.MetadataServiceAPI.Repositories.CreatedPipelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class CreatedPipelineService extends CreateAndReadService<CreatedPipeline, Long>
{
    @Autowired
    CreatedPipelineRepository createdPipelineRepository;


    @Autowired
    private EntityManager entityManager;

    public CreatedPipelineService(JpaRepository<CreatedPipeline, Long> repository, CreatedPipelineRepository createdPipelineRepository, EntityManager entityManager)
    {
        super(repository);
        this.createdPipelineRepository = createdPipelineRepository;
        this.entityManager = entityManager;
    }

    public void delete(CreatedPipeline createdPipeline)
    {
        createdPipelineRepository.delete(createdPipeline);
    }

    public CreatedPipeline getNextToProcess()
    {
        List<CreatedPipeline> resultList = entityManager.createQuery("SELECT pipeline FROM CreatedPipeline pipeline WHERE pipeline.state = 1", CreatedPipeline.class).setMaxResults(1).getResultList();
        if (resultList.isEmpty())
            return null;

        return resultList.get(0);
    }
}
