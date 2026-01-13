package com.ead.authuser.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    static final int TIMEOUT = 5000;

    @LoadBalanced  // Adiciona balanceamento de carga
    @Bean
    public RestTemplate restTemplate (
            RestTemplateBuilder builder
    ) {
        return builder
                .setConnectTimeout(Duration.ofMillis(TIMEOUT)) // tempo máximo que o cliente espera para conseguir se conectar ao servidor
                .setReadTimeout(Duration.ofMillis(TIMEOUT)) // tempo máximo que o cliente espera pela resposta do servidor, após a conexão já ter sido estabelecida
                .build();
    }

}