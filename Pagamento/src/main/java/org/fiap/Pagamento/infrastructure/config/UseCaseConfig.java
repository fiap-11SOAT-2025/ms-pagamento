package org.fiap.Pagamento.infrastructure.config;


import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.core.gateways.PagamentoGateway;
import org.fiap.Pagamento.core.gateways.PedidoGateway;
import org.fiap.Pagamento.core.gateways.ProducaoGateway;
import org.fiap.Pagamento.core.usercases.*;
import org.fiap.Pagamento.core.usercases.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public MercadoPagoUserCases mercadoPagoUserCases(MercadoPagoGateway mercadoPagoGateway,
                                                     PedidoUseCases pedidoUseCases) {
        return new MercadoPagoServiceImpl(mercadoPagoGateway, pedidoUseCases);
    }

    @Bean
    public PagamentoUseCases pagamentoUseCases(PagamentoGateway pagamentoGateway,
                                               QrCodeUserCases qrCodeUserCases,
                                               PedidoUseCases pedidoUseCases,
                                               ProducaoUseCases producaoUseCases,
                                               MercadoPagoUserCases mercadoPagoUserCases) {
        return new PagamentoServiceImpl(pagamentoGateway, qrCodeUserCases, pedidoUseCases, mercadoPagoUserCases, producaoUseCases);
    }

    @Bean
    public PedidoUseCases pedidoUseCases(PedidoGateway pedidoGateway) {
        return new PedidoServiceImpl(pedidoGateway);
    }

    @Bean
    public ProducaoUseCases producaoUseCases(ProducaoGateway producaoGateway) {
        return new ProducaoServiceImpl(producaoGateway);
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
