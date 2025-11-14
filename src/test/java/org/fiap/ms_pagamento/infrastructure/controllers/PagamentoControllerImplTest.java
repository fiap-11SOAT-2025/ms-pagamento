package org.fiap.ms_pagamento.infrastructure.controllers;

import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.usercases.PagamentoUseCases;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoControllerImplTest {

    private PagamentoUseCases pagamentoUseCases;
    private PagamentoControllerImpl pagamentoController;

    @BeforeEach
    void setUp() {
        pagamentoUseCases = mock(PagamentoUseCases.class);
        pagamentoController = new PagamentoControllerImpl(pagamentoUseCases);
    }

    @Test
    void deveGerarQrCodePagamentoMercadoPago() {
        // Arrange
        String idPedido = "PED123";
        Pagamento pagamentoMock = new Pagamento(1L, idPedido, "qrcode", "extRef", 1);
        when(pagamentoUseCases.geraQrCodePagamentoMercadoPago(idPedido)).thenReturn(pagamentoMock);

        // Act
        Pagamento resultado = pagamentoController.geraQrCodePagamentoMercadoPago(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(pagamentoMock, resultado);
        verify(pagamentoUseCases, times(1)).geraQrCodePagamentoMercadoPago(idPedido);
    }

    @Test
    void deveVisualizarQrCodePagamentoMercadoPago() {
        // Arrange
        String idPedido = "PED001";
        byte[] qrCode = new byte[]{1, 2, 3};
        when(pagamentoUseCases.vizualizarQrCodePagamentoMercadoPago(idPedido)).thenReturn(qrCode);

        // Act
        byte[] resultado = pagamentoController.vizualizarQrCodePagamentoMercadoPago(idPedido);

        // Assert
        assertNotNull(resultado);
        assertArrayEquals(qrCode, resultado);
        verify(pagamentoUseCases, times(1)).vizualizarQrCodePagamentoMercadoPago(idPedido);
    }

    @Test
    void deveBuscarPagamentos() {
        // Arrange
        List<Pagamento> listaMock = List.of(
                new Pagamento(1L, "PED1", "qr1", "ext1", 1),
                new Pagamento(2L, "PED2", "qr2", "ext2", 2)
        );
        when(pagamentoUseCases.buscaPagamentos()).thenReturn(listaMock);

        // Act
        List<Pagamento> resultado = pagamentoController.buscaPagamentos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pagamentoUseCases, times(1)).buscaPagamentos();
    }

    @Test
    void deveConsultarStatusPagamento() {
        // Arrange
        String idPedido = "PED999";
        Pagamento pagamentoMock = new Pagamento(99L, idPedido, "qr", "ext", 3);
        when(pagamentoUseCases.buscaPagamentoPorPedidoId(idPedido)).thenReturn(pagamentoMock);

        // Act
        Pagamento resultado = pagamentoController.consultaStatusPagamento(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(pagamentoMock, resultado);
        verify(pagamentoUseCases, times(1)).buscaPagamentoPorPedidoId(idPedido);
    }
}
