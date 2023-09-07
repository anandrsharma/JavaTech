package com.programmingtechie.inventoryservice.controller;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
	@Autowired
    private InventoryService inventoryService;
	//http://localhost:8082/api/inventory/iphone13,iphone13red
	//http://localhost:8082/api/inventory?skuCode=iphone13&skuCode=iphone13red
	@GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		log.info("Inventry Controller");
    	return inventoryService.isInStock(skuCode);
    }
}

