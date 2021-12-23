package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Repositories;

import com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI.Models.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long>
{
}
