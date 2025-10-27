package org.fiap.Pagamento.infrastructure.config;


import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.core.gateways.PagamentoGateway;
import org.fiap.Pagamento.core.usercases.*;
import org.fiap.Pagamento.core.usercases.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public MercadoPagoUserCases mercadoPagoUserCases(MercadoPagoGateway mercadoPagoGateway) {
        return new MercadoPagoServiceImpl(mercadoPagoGateway);
    }

    @Bean
    public PagamentoUseCases pagamentoUseCases(PagamentoGateway pagamentoGateway,
                                               QrCodeUserCases qrCodeUserCases,
                                               PedidoUseCases pedidoUseCases,
                                               MercadoPagoUserCases mercadoPagoUserCases) {
        return new PagamentoServiceImpl(pagamentoGateway, qrCodeUserCases, pedidoUseCases, mercadoPagoUserCases);
    }

    @Bean
    public PedidoUseCases pedidoUseCases() {
        return new PedidoServiceImpl();
    }

    @Bean
    public QrCodeUserCases qrCodeUserCases() {
        return new QrCodeService();
    }

    @Bean
    public WebhookUseCases webhookUseCases(MercadoPagoGateway mercadoPagoGateway, PagamentoUseCases pagamentoUseCases){
        return new WebhookServiceImpl(mercadoPagoGateway,pagamentoUseCases);
    }
}
