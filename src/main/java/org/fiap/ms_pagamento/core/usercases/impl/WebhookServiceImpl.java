package org.fiap.ms_pagamento.core.usercases.impl;


import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.ms_pagamento.core.usercases.PagamentoUseCases;
import org.fiap.ms_pagamento.core.usercases.WebhookUseCases;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.StatusMerchantOrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.WebhookResponseDTO;

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
