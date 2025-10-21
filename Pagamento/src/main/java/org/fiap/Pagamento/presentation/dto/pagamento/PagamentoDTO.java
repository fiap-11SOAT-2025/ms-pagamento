package org.fiap.Pagamento.presentation.dto.pagamento;

public record PagamentoDTO(Long id, String qrCodeMercadoPago, String externalReferenceMercadoPago, String statusPagamento) {

}
