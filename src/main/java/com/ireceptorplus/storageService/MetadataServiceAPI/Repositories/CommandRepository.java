package com.ireceptorplus.storageService.MetadataServiceAPI.Repositories;

import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long>
{
}
