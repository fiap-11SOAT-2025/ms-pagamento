package org.fiap.ms_pagamento.presentation.dto.pagamento;

public record PagamentoDTO(Long id, String qrCodeMercadoPago, String externalReferenceMercadoPago, String statusPagamento) {

}
