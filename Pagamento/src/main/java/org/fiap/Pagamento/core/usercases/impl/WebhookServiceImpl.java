package org.fiap.Pagamento.core.usercases.impl;


import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.core.usercases.PagamentoUseCases;
import org.fiap.Pagamento.core.usercases.WebhookUseCases;
import org.fiap.Pagamento.presentation.dto.mercadopago.StatusMerchantOrderMercadoPagoDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.WebhookResponseDTO;

public class WebhookServiceImpl implements WebhookUseCases {

    MercadoPagoGateway mercadoPagoGateway;
    PagamentoUseCases pagamentoUseCases;

    public WebhookServiceImpl(MercadoPagoGateway mercadoPagoGateway, PagamentoUseCases pagamentoUseCases) {
        this.mercadoPagoGateway = mercadoPagoGateway;
        this.pagamentoUseCases = pagamentoUseCases;
    }

    @Override
    public void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO) {

        String idMerchntOrder = webhookResponseDTO.id();
        StatusMerchantOrderMercadoPagoDTO statusMerchantOrderMercadoPagoDTO = mercadoPagoGateway.statusMerchantOrder(idMerchntOrder);

        Pagamento pagamento = pagamentoUseCases.buscaPagamentoPorExternalReference(statusMerchantOrderMercadoPagoDTO.external_reference());
        if (statusMerchantOrderMercadoPagoDTO.status().equals("closed"))
            pagamentoUseCases.atualizaPagamentoPedido(pagamento);
    }


}
