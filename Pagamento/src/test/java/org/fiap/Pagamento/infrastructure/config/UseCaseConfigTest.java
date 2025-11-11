package org.fiap.Pagamento.infrastructure.config;

import org.fiap.Pagamento.core.gateways.*;
import org.fiap.Pagamento.core.usercases.*;
import org.fiap.Pagamento.core.usercases.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UseCaseConfigTest {

    private UseCaseConfig config;

    // Mocks dos gateways
    private MercadoPagoGateway mercadoPagoGateway;
    private PagamentoGateway pagamentoGateway;
    private PedidoGateway pedidoGateway;
    private ProducaoGateway producaoGateway;

    @BeforeEach
    void setUp() {
        config = new UseCaseConfig();
        mercadoPagoGateway = mock(MercadoPagoGateway.class);
        pagamentoGateway = mock(PagamentoGateway.class);
        pedidoGateway = mock(PedidoGateway.class);
        producaoGateway = mock(ProducaoGateway.class);
    }

    @Test
    void deveCriarBeanDeQrCodeUserCases() {
        QrCodeUserCases qrCodeUserCases = config.qrCodeUserCases();

        assertNotNull(qrCodeUserCases);
        assertInstanceOf(QrCodeService.class, qrCodeUserCases);
    }

    @Test
    void deveCriarBeanDePedidoUseCases() {
        PedidoUseCases pedidoUseCases = config.pedidoUseCases(pedidoGateway);

        assertNotNull(pedidoUseCases);
        assertInstanceOf(PedidoServiceImpl.class, pedidoUseCases);
    }

    @Test
    void deveCriarBeanDeProducaoUseCases() {
        ProducaoUseCases producaoUseCases = config.producaoUseCases(producaoGateway);

        assertNotNull(producaoUseCases);
        assertInstanceOf(ProducaoServiceImpl.class, producaoUseCases);
    }

    @Test
    void deveCriarBeanDeMercadoPagoUserCases() {
        PedidoUseCases pedidoUseCases = mock(PedidoUseCases.class);
        MercadoPagoUserCases mercadoPagoUserCases = config.mercadoPagoUserCases(mercadoPagoGateway, pedidoUseCases);

        assertNotNull(mercadoPagoUserCases);
        assertInstanceOf(MercadoPagoServiceImpl.class, mercadoPagoUserCases);
    }

    @Test
    void deveCriarBeanDePagamentoUseCases() {
        QrCodeUserCases qrCodeUserCases = mock(QrCodeUserCases.class);
        PedidoUseCases pedidoUseCases = mock(PedidoUseCases.class);
        ProducaoUseCases producaoUseCases = mock(ProducaoUseCases.class);
        MercadoPagoUserCases mercadoPagoUserCases = mock(MercadoPagoUserCases.class);

        PagamentoUseCases pagamentoUseCases = config.pagamentoUseCases(
                pagamentoGateway, qrCodeUserCases, pedidoUseCases, producaoUseCases, mercadoPagoUserCases);

        assertNotNull(pagamentoUseCases);
        assertInstanceOf(PagamentoServiceImpl.class, pagamentoUseCases);
    }

    @Test
    void deveCriarBeanDeWebhookUseCases() {
        PagamentoUseCases pagamentoUseCases = mock(PagamentoUseCases.class);
        WebhookUseCases webhookUseCases = config.webhookUseCases(mercadoPagoGateway, pagamentoUseCases);

        assertNotNull(webhookUseCases);
        assertInstanceOf(WebhookServiceImpl.class, webhookUseCases);
    }
}
