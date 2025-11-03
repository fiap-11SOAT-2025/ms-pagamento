package org.fiap.Pagamento.infrastructure.controllers;


import org.fiap.Pagamento.core.controller.PagamentoController;
import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.core.usercases.PagamentoUseCases;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoControllerImpl implements PagamentoController {

    private PagamentoUseCases pagamentoUseCases;

    public PagamentoControllerImpl(PagamentoUseCases pagamentoUseCases) {
        this.pagamentoUseCases = pagamentoUseCases;
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
