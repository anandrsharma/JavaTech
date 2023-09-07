package com.programming.techie.apigateway.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Base64;

public class AccountPreFilter extends AbstractGatewayFilterFactory<AccountPreFilter.Config> {
	final Logger logger = LoggerFactory.getLogger(LoggingGlobalPreFilter.class);
    public AccountPreFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        String auth = "digi" + ":" + "digi";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String basic = "Basic "+encodedAuth;

        logger.info("Inside Account Pre Filter");
        return new OrderedGatewayFilter((exchange, chain) -> {
        	logger.info("Intercepted request to account");
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("Authorization", basic)
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        }, 1);
    }

    @Getter
    @Setter
    public static class Config {
        private String name;
    }
	
}
