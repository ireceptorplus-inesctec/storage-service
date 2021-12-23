package com.ireceptorplus.ireceptorchainclient;

import com.ireceptorplus.ireceptorchainclient.FileStorage.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IReceptorChainClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(IReceptorChainClientApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
