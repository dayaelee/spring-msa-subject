package com.sparta.msa_exam.gateway;


import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import reactor.core.publisher.Mono;

@Configuration
public class DebugFilter {

    @Bean
    public GlobalFilter logTargetInstance() {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            URI targetUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
            if (targetUri != null) {
                System.out.println("[DEBUG] Gateway sent request to: " + targetUri);
            } else {
                System.out.println("[DEBUG] No target URI found in exchange attributes.");
            }
        }));
    }
}
