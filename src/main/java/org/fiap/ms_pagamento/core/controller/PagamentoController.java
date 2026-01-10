package org.fiap.ms_pagamento.core.controller;

import org.fiap.ms_pagamento.core.entities.Pagamento;

import java.util.List;

public interface PagamentoController {

    Pagamento geraQrCodePagamentoMercadoPago(String idPedido);

    byte[] vizualizarQrCodePagamentoMercadoPago(String idPedido);

    List<Pagamento> buscaPagamentos();

    Pagamento consultaStatusPagamento(String idPedido);

    Pagamento geraPagamento(String idPedido);
}
