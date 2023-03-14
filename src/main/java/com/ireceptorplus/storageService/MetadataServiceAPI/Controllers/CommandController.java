package com.ireceptorplus.storageService.MetadataServiceAPI.Controllers;

import com.ireceptorplus.storageService.MetadataServiceAPI.DTOs.CommandDTO;
import com.ireceptorplus.storageService.MetadataServiceAPI.Models.Command;
import com.ireceptorplus.storageService.MetadataServiceAPI.Services.CommandService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/command")
@CrossOrigin(origins = "${app.resourceAllowedOrigins}")
public class CommandController
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommandService commandService;

    public CommandController(CommandService commandService)
    {
        this.commandService = commandService;
    }

    /**
     * Returns all Command objects stored.
     * @return An array containing all the Command objects stored.
     */
    @GetMapping
    @Operation(summary = "Returns all Command objects stored")
    public List<CommandDTO> getCommands()
    {
        List<Command> commands = commandService.readAll();
        List<CommandDTO> commandDTOs = commands.stream().map(command -> modelMapper.map(command, CommandDTO.class)).collect(Collectors.toList());

        return commandDTOs;
    }
}
