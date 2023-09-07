package com.programming.techie.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

public class LoggingGlobalFiltersConfigurations {
	final Logger logger =
		      LoggerFactory.getLogger(
		        LoggingGlobalFiltersConfigurations.class);

		    @Bean
		    public GlobalFilter postGlobalFilter() {
		        return (exchange, chain) -> {
		            return chain.filter(exchange)
		              .then(Mono.fromRunnable(() -> {
		                  logger.info("Global Post Filter executed...................");
		              }));
		        };
		    }

}
