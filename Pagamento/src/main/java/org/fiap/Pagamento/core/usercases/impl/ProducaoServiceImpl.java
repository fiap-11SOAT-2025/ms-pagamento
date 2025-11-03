package org.fiap.Pagamento.core.usercases.impl;

import org.fiap.Pagamento.core.gateways.ProducaoGateway;
import org.fiap.Pagamento.core.usercases.ProducaoUseCases;
import org.fiap.Pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.springframework.stereotype.Service;

@Service
public class ProducaoServiceImpl implements ProducaoUseCases {

    ProducaoGateway producaoGateway;

    public ProducaoServiceImpl(ProducaoGateway producaoGateway) {
        this.producaoGateway = producaoGateway;
    }

    @Override
    public void criaStatusRecebidoPedido(StatusPedidoDTO statusPedidoDTO) {
        producaoGateway.criaStatusRecebidoPedido(statusPedidoDTO);
    }
}
