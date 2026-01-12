package org.fiap.ms_pagamento.presentation.api;

import lombok.RequiredArgsConstructor;
import org.fiap.ms_pagamento.core.controller.WebhookController;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.WebhookResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookApiController {

    private final WebhookController webhookController;

    @PostMapping("/mercado-pago")
    public ResponseEntity<Void> receberWebhook(@RequestBody WebhookResponseDTO webhookResponseDTO) {

        if(webhookResponseDTO.action().equals("update"))
            webhookController.atualizaPagamentoPedido(webhookResponseDTO);

        return ResponseEntity.ok().build();
    }
}
