package com.programmingtechie.inventoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import com.programmingtechie.inventoryservice.model.*;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
}

