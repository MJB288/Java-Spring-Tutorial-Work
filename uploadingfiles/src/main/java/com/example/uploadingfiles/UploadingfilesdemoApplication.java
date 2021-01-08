package com.example.uploadingfiles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.example.uploadingfiles.storage.StorageProperties;
import com.example.uploadingfiles.storage.StorageService;

@SpringBootApplication //This is a convienience annotation
//It adds @Configuration - Tags the class as a source of bean definitions for the
//application context
//@EnableAutoConfiguration - add beans on the classpath settings, other beans 
//@ComponentScan - look for other components, configurations, services in com/example
@EnableConfigurationProperties(StorageProperties.class)
public class UploadingfilesdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadingfilesdemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
