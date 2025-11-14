package org.fiap.ms_pagamento.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest {

    private WebClientConfig webClientConfig;

    @BeforeEach
    void setUp() {
        webClientConfig = new WebClientConfig();
    }

    @Test
    void deveCriarBeanDeWebClientBuilder() {
        // Act
        WebClient.Builder builder = webClientConfig.webClientBuilder();

        // Assert
        assertNotNull(builder, "O builder do WebClient não deve ser nulo");
        assertInstanceOf(WebClient.Builder.class, builder);

        // Cria um WebClient para garantir que o builder funciona
        WebClient webClient = builder.build();
        assertNotNull(webClient, "O WebClient construído não deve ser nulo");
    }
}
