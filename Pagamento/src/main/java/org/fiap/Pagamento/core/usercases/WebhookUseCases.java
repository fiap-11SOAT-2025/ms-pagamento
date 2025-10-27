package org.fiap.Pagamento.core.usercases;

import org.fiap.fastfoodfase1.presentation.dto.mercadopago.WebhookResponseDTO;

public interface WebhookUseCases {
    void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO);
}
