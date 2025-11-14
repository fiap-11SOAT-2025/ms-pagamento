package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.gateways.PedidoGateway;
import org.fiap.ms_pagamento.presentation.dto.pedido.ItemPedidoDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidoServiceImplTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoDTO = new PedidoDTO(
                "PED001",
                List.of(new ItemPedidoDTO("Produto", 2, new BigDecimal("20.00"))),
                "NOVO",
                "CLI123",
                new BigDecimal("40.00")
        );
    }

    @Test
    void deveBuscarPedidoPorIdComSucesso() {
        // Arrange
        when(pedidoGateway.buscarPedidoPorId("PED001")).thenReturn(pedidoDTO);

        // Act
        PedidoDTO resultado = pedidoService.buscarPedidoPorId("PED001");

        // Assert
        assertEquals("PED001", resultado.id());
        assertEquals("CLI123", resultado.clienteId());
        assertEquals(new BigDecimal("40.00"), resultado.valorTotal());
        verify(pedidoGateway, times(1)).buscarPedidoPorId("PED001");
    }
}
