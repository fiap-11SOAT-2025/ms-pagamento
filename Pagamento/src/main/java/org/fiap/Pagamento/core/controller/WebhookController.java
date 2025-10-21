package org.fiap.Pagamento.core.controller;

import org.fiap.Pagamento.presentation.dto.mercadopago.WebhookResponseDTO;

public interface WebhookController {

    void atualizaPagamentoPedido(WebhookResponseDTO webhookResponseDTO);

}
