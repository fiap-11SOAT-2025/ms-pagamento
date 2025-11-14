package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.ms_pagamento.core.usercases.PedidoUseCases;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.*;
import org.fiap.ms_pagamento.presentation.dto.pedido.ItemPedidoDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MercadoPagoServiceImplTest {

    @Mock
    private MercadoPagoGateway mercadoPagoGateway;

    @Mock
    private PedidoUseCases pedidoUseCases;

    @InjectMocks
    private MercadoPagoServiceImpl mercadoPagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mercadoPagoService.externalPosId = "POS123"; // Simula o @Value injetado
    }

    @Test
    void deveGerarOrderMercadoPagoCorretamente() {
        // Arrange
        ItemPedidoDTO item = new ItemPedidoDTO("Produto Teste", 2, new BigDecimal("15.50"));
        PedidoDTO pedidoDTO = new PedidoDTO("PED001", List.of(item), "NOVO", "CLI123", new BigDecimal("31.00"));

        // Act
        OrderMercadoPagoDTO order = mercadoPagoService.geraOrderMercadoPago(pedidoDTO, new BigDecimal("31.00"));

        // Assert
        assertNotNull(order);
        assertEquals("qr", order.type());
        assertTrue(order.external_reference().startsWith("MCPG00-PED001"));
        assertNotNull(order.config());
        assertEquals("POS123", order.config().qr().external_pos_id());
        assertEquals("31.00", order.transactions().payments().get(0).amount());
        assertEquals(1, order.items().size());
        assertEquals("Produto Teste", order.items().get(0).title());
        assertEquals(2, order.items().get(0).quantity());
        assertEquals("15.50", order.items().get(0).unit_price());
    }

    @Test
    void deveGerarItensMercadoPagoCorretamente() {
        // Arrange
        List<ItemPedidoDTO> itens = List.of(
                new ItemPedidoDTO("Produto A", 1, new BigDecimal("10.00")),
                new ItemPedidoDTO("Produto B", 2, new BigDecimal("5.00"))
        );

        // Act
        List<ItemsMercadoPagoOrderDTO> itensGerados = mercadoPagoService.geraItensMercadoPago(itens);

        // Assert
        assertEquals(2, itensGerados.size());
        assertEquals("Produto A", itensGerados.get(0).title());
        assertEquals(1, itensGerados.get(0).quantity());
        assertEquals("10.00", itensGerados.get(0).unit_price());
        assertEquals("Produto B", itensGerados.get(1).title());
        assertEquals(2, itensGerados.get(1).quantity());
    }

    @Test
    void deveChamarGatewayParaGerarQrCodePagamento() {
        // Arrange
        PedidoDTO pedidoDTO = new PedidoDTO("PED001", List.of(), "NOVO", "CLI123", new BigDecimal("0"));
        OrderMercadoPagoDTO orderDTO = mock(OrderMercadoPagoDTO.class);

        MercadoPagoOrderResponseDTO responseEsperado = new MercadoPagoOrderResponseDTO(
                "order123",
                "type",
                "automatic",
                "ext123",
                "100.00",
                "2025-11-05T12:00:00Z",
                "BR",
                "user123",
                "open",
                "waiting_payment",
                "BRL",
                "2025-11-05T11:00:00Z",
                "2025-11-05T11:05:00Z",
                null,
                null,
                null,
                List.of(),
                null
        );

        when(mercadoPagoGateway.geraQrCodePagamento(orderDTO, pedidoDTO)).thenReturn(responseEsperado);

        // Act
        MercadoPagoOrderResponseDTO resultado = mercadoPagoService.geraQrCodePagamento(orderDTO, pedidoDTO);

        // Assert
        assertEquals("order123", resultado.id());
        assertEquals("ext123", resultado.external_reference());
        assertEquals("open", resultado.status());
        verify(mercadoPagoGateway, times(1)).geraQrCodePagamento(orderDTO, pedidoDTO);
    }
}
