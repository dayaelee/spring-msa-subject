package com.sparta.msa_exam.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    @Bean
    public GlobalFilter addServerPortHeader() {
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    String port = exchange.getRequest().getURI().getPort() == -1
                            ? "unknown"
                            : String.valueOf(exchange.getRequest().getURI().getPort());
                    exchange.getResponse().getHeaders().add("Server-Port", port);
                }));
    }
}