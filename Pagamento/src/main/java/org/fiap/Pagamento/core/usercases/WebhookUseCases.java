package org.fiap.Pagamento.core.usercases;


import org.fiap.Pagamento.presentation.dto.mercadopago.WebhookResponseDTO;

public interface WebhookUseCases {
    void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO);
}
