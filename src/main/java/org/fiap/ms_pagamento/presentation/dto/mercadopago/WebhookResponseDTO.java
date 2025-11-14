package org.fiap.ms_pagamento.presentation.dto.mercadopago;

public record WebhookResponseDTO(String action, String application_id, MercadoPagoDataDTO data, String date_created, String id, boolean live_mode, String status, String type,Long user_id, String version) {
}
