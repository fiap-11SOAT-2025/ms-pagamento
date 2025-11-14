package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.gateways.ProducaoGateway;
import org.fiap.ms_pagamento.core.usercases.ProducaoUseCases;
import org.fiap.ms_pagamento.presentation.dto.producao.StatusPedidoDTO;
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
