package org.fiap.ms_pagamento.core.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void deveCriarPagamentoComDadosValidos() {
        Pagamento pagamento = new Pagamento(
                1L,
                "PED123",
                "QR123",
                "EXT123",
                2
        );

        assertEquals(1L, pagamento.getId());
        assertEquals("PED123", pagamento.getPedidoId());
        assertEquals("QR123", pagamento.getQrCodeMercadoPago());
        assertEquals("EXT123", pagamento.getExternalReferenceMercadoPago());
        assertEquals(2, pagamento.getStatusPagamentoId());
    }

    @Test
    void deveLancarExcecaoQuandoPedidoIdForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Pagamento(1L, null, "QR", "EXT", 1)
        );

        assertEquals("Pagamento deve estar associado a um pedido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoStatusPagamentoIdForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Pagamento(1L, "PED123", "QR", "EXT", null)
        );

        assertEquals("Status do pagamento é obrigatório.", exception.getMessage());
    }

    @Test
    void devePermitirAlterarQrCode() {
        Pagamento pagamento = new Pagamento(1L, "PED123", "OLDQR", "EXT", 1);
        pagamento.setQrCodeMercadoPago("NEWQR");

        assertEquals("NEWQR", pagamento.getQrCodeMercadoPago());
    }

    @Test
    void devePermitirAlterarExternalReference() {
        Pagamento pagamento = new Pagamento(1L, "PED123", "QR", "OLD_EXT", 1);
        pagamento.setExternalReferenceMercadoPago("NEW_EXT");

        assertEquals("NEW_EXT", pagamento.getExternalReferenceMercadoPago());
    }

    @Test
    void devePermitirAlterarStatusPagamento() {
        Pagamento pagamento = new Pagamento(1L, "PED123", "QR", "EXT", 1);
        pagamento.setStatusPagamentoId(2);

        assertEquals(2, pagamento.getStatusPagamentoId());
    }

    @Test
    void deveCriarPagamentoComConstrutorAlternativo() {
        Pagamento pagamento = new Pagamento("PED999", "QR999", "EXT999", 3);

        assertNull(pagamento.getId());
        assertEquals("PED999", pagamento.getPedidoId());
        assertEquals("QR999", pagamento.getQrCodeMercadoPago());
        assertEquals("EXT999", pagamento.getExternalReferenceMercadoPago());
        assertEquals(3, pagamento.getStatusPagamentoId());
    }
}
