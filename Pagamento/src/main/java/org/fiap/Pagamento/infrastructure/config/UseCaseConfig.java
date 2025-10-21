package org.fiap.Pagamento.infrastructure.config;

import org.fiap.fastfoodfase1.core.gateways.*;
import org.fiap.fastfoodfase1.core.usercases.*;
import org.fiap.fastfoodfase1.core.usercases.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ClienteUseCases clienteUseCases(ClienteGateway clienteGateway) {
        return new ClienteServiceImpl(clienteGateway);
    }

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
    public PedidoUseCases pedidoUseCases(PedidoGateway pedidoGateway, ClienteUseCases clienteUseCases, ProdutoUseCases produtoUseCases,
                                         PagamentoGateway pagamentoGateway) {
        return new PedidoServiceImpl(pedidoGateway, clienteUseCases, produtoUseCases, pagamentoGateway);
    }

    @Bean
    public ProdutoUseCases produtoUseCases(ProdutoGateway produtoGateway) {
        return new ProdutoServiceImpl(produtoGateway);
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
