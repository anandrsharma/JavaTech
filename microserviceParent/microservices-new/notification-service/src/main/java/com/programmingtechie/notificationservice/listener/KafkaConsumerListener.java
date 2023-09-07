package com.programmingtechie.notificationservice.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.programmingtechie.notificationservice.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumerListener {
	
	//@Bean
	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		log.info("Received KafkaConsumerListener.handleNotification() --- information for Order - {}", orderPlacedEvent.getOrderNumber());
		// send out an email notification
	}
}
