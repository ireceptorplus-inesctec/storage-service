package com.ireceptorplus.storageService.MetadataServiceAPI.Repositories;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Script;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptRepository extends JpaRepository<Script, Long>
{
}
