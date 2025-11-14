package org.fiap.ms_pagamento.core.usercases;


import org.fiap.ms_pagamento.presentation.dto.mercadopago.WebhookResponseDTO;

public interface WebhookUseCases {
    void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO);
}
