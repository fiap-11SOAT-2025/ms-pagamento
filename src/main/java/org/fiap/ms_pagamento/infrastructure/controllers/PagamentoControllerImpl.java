package org.fiap.ms_pagamento.infrastructure.controllers;


import org.fiap.ms_pagamento.core.controller.PagamentoController;
import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.usercases.PagamentoUseCases;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoControllerImpl implements PagamentoController {

    private PagamentoUseCases pagamentoUseCases;

    public PagamentoControllerImpl(PagamentoUseCases pagamentoUseCases) {
        this.pagamentoUseCases = pagamentoUseCases;
    }

    @Override
    public Pagamento geraPagamento(String idPedido) {
        return pagamentoUseCases.geraPagamento(idPedido);
    }

    @Override
    public Pagamento geraQrCodePagamentoMercadoPago(String idPedido) {
        return pagamentoUseCases.geraQrCodePagamentoMercadoPago(idPedido);
    }

    @Override
    public byte[] vizualizarQrCodePagamentoMercadoPago(String idPedido) {
        return pagamentoUseCases.vizualizarQrCodePagamentoMercadoPago(idPedido);
    }

    @Override
    public List<Pagamento> buscaPagamentos() {
        return pagamentoUseCases.buscaPagamentos();
    }

    @Override
    public Pagamento consultaStatusPagamento(String idPedido) {
        return pagamentoUseCases.buscaPagamentoPorPedidoId(idPedido);
    }
}
