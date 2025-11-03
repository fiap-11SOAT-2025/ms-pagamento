package org.fiap.Pagamento.core.controller;

import org.fiap.Pagamento.core.entities.Pagamento;

import java.util.List;

public interface PagamentoController {

    Pagamento geraQrCodePagamentoMercadoPago(String idPedido);

    byte[] vizualizarQrCodePagamentoMercadoPago(String idPedido);

    List<Pagamento> buscaPagamentos();

    Pagamento consultaStatusPagamento(String idPedido);
}
