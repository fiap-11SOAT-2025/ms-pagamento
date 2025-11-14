package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.ms_pagamento.core.usercases.PagamentoUseCases;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.MercadoPagoDataDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.StatusMerchantOrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.WebhookResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

class WebhookServiceImplTest {

    @Mock
    private MercadoPagoGateway mercadoPagoGateway;

    @Mock
    private PagamentoUseCases pagamentoUseCases;

    @InjectMocks
    private WebhookServiceImpl webhookService;

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pagamento = new Pagamento(1L, "PED001", "qrCode123", "ext123", 2);
    }

    private WebhookResponseDTO criarWebhookResponse(String idMerchantOrder) {
        return new WebhookResponseDTO(
                "payment.created",       // action
                "app123",                // application_id
                new MercadoPagoDataDTO("BRL", "MP", "active"), // data
                "2025-11-05T12:00:00Z",  // date_created
                idMerchantOrder,         // id
                true,                    // live_mode
                "active",                // status
                "merchant_order",        // type
                12345L,                  // user_id
                "1.0"                    // version
        );
    }

    private StatusMerchantOrderMercadoPagoDTO criarStatusMerchantOrder(String externalReference, String status) {
        return new StatusMerchantOrderMercadoPagoDTO(
                1001L,
                status,
                externalReference,
                null,
                List.of(),
                null,
                null,
                null,
                null,
                null,
                null,
                BigDecimal.ZERO,
                BigDecimal.TEN,
                null,
                BigDecimal.TEN,
                BigDecimal.ZERO,
                null,
                List.of(),
                false,
                null,
                null,
                null
        );
    }

    @Test
    void deveAtualizarPagamentoQuandoStatusForClosed() {
        // Arrange
        WebhookResponseDTO webhookResponseDTO = criarWebhookResponse("order123");
        StatusMerchantOrderMercadoPagoDTO statusDTO = criarStatusMerchantOrder("ext123", "closed");

        when(mercadoPagoGateway.statusMerchantOrder("order123")).thenReturn(statusDTO);
        when(pagamentoUseCases.buscaPagamentoPorExternalReference("ext123")).thenReturn(pagamento);

        // Act
        webhookService.atualizaPagamentoPedido(webhookResponseDTO);

        // Assert
        verify(mercadoPagoGateway, times(1)).statusMerchantOrder("order123");
        verify(pagamentoUseCases, times(1)).buscaPagamentoPorExternalReference("ext123");
        verify(pagamentoUseCases, times(1)).atualizaPagamentoPedido(pagamento);
    }

    @Test
    void naoDeveAtualizarPagamentoQuandoStatusNaoForClosed() {
        // Arrange
        WebhookResponseDTO webhookResponseDTO = criarWebhookResponse("order123");
        StatusMerchantOrderMercadoPagoDTO statusDTO = criarStatusMerchantOrder("ext123", "opened");

        when(mercadoPagoGateway.statusMerchantOrder("order123")).thenReturn(statusDTO);
        when(pagamentoUseCases.buscaPagamentoPorExternalReference("ext123")).thenReturn(pagamento);

        // Act
        webhookService.atualizaPagamentoPedido(webhookResponseDTO);

        // Assert
        verify(mercadoPagoGateway, times(1)).statusMerchantOrder("order123");
        verify(pagamentoUseCases, times(1)).buscaPagamentoPorExternalReference("ext123");
        verify(pagamentoUseCases, never()).atualizaPagamentoPedido(any());
    }
}
