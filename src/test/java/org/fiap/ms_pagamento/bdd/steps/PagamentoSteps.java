package org.fiap.ms_pagamento.bdd.steps;

import io.cucumber.java.pt.*;
import org.fiap.ms_pagamento.core.controller.PagamentoController;
import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.enums.StatusPagamentoEnum;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PagamentoSteps {

    @Mock
    private PagamentoController pagamentoController; // Interface do seu PagamentoControllerImpl

    private Pagamento responsePagamento;
    private String pedidoIdAtual;

    public PagamentoSteps() {
        MockitoAnnotations.openMocks(this);
    }

    // --- Cenário: Gerar QR Code ---
    @Dado("que tenho um pedido pendente com ID {string}")
    public void setupPedidoPendente(String pedidoId) {
        this.pedidoIdAtual = pedidoId;

        // Mock do retorno da geração do QR Code
        Pagamento pagamentoMock = new Pagamento(
                null,
                pedidoId,
                "00020101021243650016COM.MERCADOLIBRE...",
                "00020101021243650016EXT.MERCADOLIBRE...",
                StatusPagamentoEnum.PENDENTE_PAGAMENTO.getCodigo()
        );

        when(pagamentoController.geraQrCodePagamentoMercadoPago(pedidoId))
                .thenReturn(pagamentoMock);
    }

    @Quando("eu solicito a geração do QR Code de pagamento para este pedido")
    public void solicitarQrCode() {
        this.responsePagamento = pagamentoController.geraQrCodePagamentoMercadoPago(this.pedidoIdAtual);
    }

    @Então("o sistema deve retornar os dados do pagamento com status {string}")
    public void validarStatusPagamento(String statusEsperado) {
        assertNotNull(responsePagamento);
        assertEquals(statusEsperado,
                StatusPagamentoEnum.
                        fromCodigo(responsePagamento.getStatusPagamentoId()).name());
    }

    @E("deve conter o código do QR Code \\(QR Data)")
    public void validarQrCodeData() {
        assertNotNull(responsePagamento.getQrCodeMercadoPago());
        assertFalse(responsePagamento.getQrCodeMercadoPago().isEmpty());
    }

    // --- Cenário: Consultar Status ---

    @Dado("que existe um pagamento processado para o pedido {string} com status {string}")
    public void setupPagamentoExistente(String pedidoId, String status) {
        // Mock do retorno da consulta
        Pagamento pagamentoMock = new Pagamento(
                null,
                pedidoId,
                "00020101021243650016COM.MERCADOLIBRE...",
                "00020101021243650016EXT.MERCADOLIBRE...",
                StatusPagamentoEnum.PAGO.getCodigo()
        );

        when(pagamentoController.consultaStatusPagamento(pedidoId))
                .thenReturn(pagamentoMock);
    }

    @Quando("eu consulto o status do pagamento pelo ID do pedido {string}")
    public void consultarStatus(String pedidoId) {
        this.responsePagamento = pagamentoController.consultaStatusPagamento(pedidoId);
    }

    @Então("o sistema deve retornar que o status é {string}")
    public void validarStatusRetornado(String statusEsperado) {
        assertNotNull(responsePagamento);
        assertEquals(statusEsperado,
                StatusPagamentoEnum.
                        fromCodigo(responsePagamento.getStatusPagamentoId()).name());
    }
}