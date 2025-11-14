package org.fiap.ms_pagamento.core.controller;

import org.fiap.ms_pagamento.presentation.dto.mercadopago.WebhookResponseDTO;

public interface WebhookController {

    void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO);

}
