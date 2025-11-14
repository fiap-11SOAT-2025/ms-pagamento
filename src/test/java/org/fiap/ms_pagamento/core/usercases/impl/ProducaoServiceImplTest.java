package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.gateways.ProducaoGateway;
import org.fiap.ms_pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ProducaoServiceImplTest {

    @Mock
    private ProducaoGateway producaoGateway;

    @InjectMocks
    private ProducaoServiceImpl producaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveChamarGatewayParaCriarStatusRecebidoPedido() {
        // Arrange
        StatusPedidoDTO statusPedidoDTO = new StatusPedidoDTO("PED001");

        // Act
        producaoService.criaStatusRecebidoPedido(statusPedidoDTO);

        // Assert
        verify(producaoGateway, times(1)).criaStatusRecebidoPedido(statusPedidoDTO);
        verifyNoMoreInteractions(producaoGateway);
    }
}
