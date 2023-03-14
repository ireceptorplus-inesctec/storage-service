package com.ireceptorplus.storageService.MetadataServiceAPI.Repositories;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long>
{
    List<Tool> findAll();
    Tool findByName(String name);

}
