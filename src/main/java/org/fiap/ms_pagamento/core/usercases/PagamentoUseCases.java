package org.fiap.ms_pagamento.core.usercases;



import org.fiap.ms_pagamento.core.entities.Pagamento;

import java.util.List;

public interface PagamentoUseCases {

    Pagamento geraQrCodePagamentoMercadoPago(String idPedido);

    byte[] vizualizarQrCodePagamentoMercadoPago(String idPedido);

    Pagamento buscaPagamentoPorPedidoId(String idPedido);

    Pagamento buscaPagamentoPorExternalReference(String externalReference);

    void atualizaPagamentoPedido(Pagamento pagamento);

    List<Pagamento> buscaPagamentos();

    Pagamento geraPagamento(String idPedido);
}
