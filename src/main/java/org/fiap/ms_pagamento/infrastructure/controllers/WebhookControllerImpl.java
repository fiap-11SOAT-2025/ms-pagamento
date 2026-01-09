package org.fiap.ms_pagamento.infrastructure.controllers;


import org.fiap.ms_pagamento.core.controller.WebhookController;
import org.fiap.ms_pagamento.core.usercases.WebhookUseCases;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.WebhookResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class WebhookControllerImpl implements WebhookController {

    private WebhookUseCases webhookUseCases;

    public WebhookControllerImpl(WebhookUseCases webhookUseCases) {
        this.webhookUseCases = webhookUseCases;
    }

    @Override
    public void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO) {

        webhookUseCases.atualizaPagamentoPedido(webhookResponseDTO);

    }
}
