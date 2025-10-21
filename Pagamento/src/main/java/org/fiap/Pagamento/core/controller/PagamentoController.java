package org.fiap.Pagamento.core.controller;

import org.fiap.Pagamento.core.entities.Pagamento;

import java.util.List;

public interface PagamentoController {

    Pagamento geraQrCodePagamentoMercadoPago(Long idPedido);

    byte[] vizualizarQrCodePagamentoMercadoPago(Long idPedido);

    List<Pagamento> buscaPagamentos();

    Pagamento consultaStatusPagamento(Long idPedido);
}
