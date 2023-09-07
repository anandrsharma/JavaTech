package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    //@SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
    	log.info("InventoryService -> isInStock() ");
        
    	return inventoryRepository.findBySkuCodeIn(skuCode).stream()
    			.map(inventory ->
    			InventoryResponse.builder()
    				.skuCode(inventory.getSkuCode())
    				.isInStock(inventory.getQuantity() > 0)
    				.build()
    			).collect(Collectors.toList());
    }
}
