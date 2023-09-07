package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.config.WebClientConfig;
import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.event.OrderPlacedEvent;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	@Autowired
    private OrderRepository orderRepository;
	@Autowired
	private WebClient.Builder webClientBuilder;
	private final Tracer tracer;
	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;
	
    public String placeOrder(OrderRequest orderRequest) {
    	final StopWatch stopWatch = new StopWatch();
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
                //.toList();
        order.setOrderLineItemsList(orderLineItems);
        
        //Call Inventory service, place a order if it is available in stock
        List<String> skuCodes = order.getOrderLineItemsList().stream()
        	.map(OrderLineItems::getSkuCode)
        	.collect(Collectors.toList());
        
        Span inventroyServiceLookuoSpan = tracer.nextSpan().name("InventryServiceLookup");
        try (
        	Tracer.SpanInScope spanInScope = tracer.withSpan(inventroyServiceLookuoSpan.start()) 
        	) {
        	InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                	.uri("http://inventory-service/api/inventory",
                			uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                	.retrieve()
                	.bodyToMono(InventoryResponse[].class)
                	.block();
                
            Boolean result = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
            if(result) {	
            	orderRepository.save(order);
            	//kafkaTemplate.send("notificationTopic", order.getOrderNumber() );
                return "Order Placed successfully";
            } else {
            	throw new IllegalArgumentException("Product is not in stock, Please try again later");
            }
        } finally {
			inventroyServiceLookuoSpan.end();
		}        
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
