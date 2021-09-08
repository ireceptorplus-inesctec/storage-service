package com.ireceptorplus.ireceptorchainclient;

import com.ireceptorplus.ireceptorchainclient.DatasetStorage.storage.StorageService;
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
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
