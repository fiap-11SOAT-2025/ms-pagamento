package org.fiap.ms_pagamento.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigurationTest {

    @Test
    void deveCriarOpenAPIComInformacoesCorretas() {
        // Arrange
        OpenApiConfiguration configuration = new OpenApiConfiguration();

        // Act
        OpenAPI openAPI = configuration.customOpenAPI();
        Info info = openAPI.getInfo();

        // Assert
        assertNotNull(openAPI, "OpenAPI não deve ser nulo");
        assertNotNull(info, "Info não deve ser nulo");

        assertEquals("API Pagamento", info.getTitle());
        assertEquals("Documentação da API de Pagamento.", info.getDescription());
        assertEquals("v1.0.0", info.getVersion());

        assertNotNull(info.getContact(), "Contato não deve ser nulo");
        assertEquals("Equipe de Desenvolvimento", info.getContact().getName());
        assertEquals("dev@fastfood.com.br", info.getContact().getEmail());

        assertNotNull(info.getLicense(), "Licença não deve ser nula");
        assertEquals("Apache 2.0", info.getLicense().getName());
        assertEquals("http://springdoc.org", info.getLicense().getUrl());
    }
}
