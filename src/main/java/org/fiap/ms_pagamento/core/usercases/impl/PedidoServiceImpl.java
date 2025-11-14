package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.gateways.PedidoGateway;
import org.fiap.ms_pagamento.core.usercases.PedidoUseCases;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl  implements PedidoUseCases {

    PedidoGateway pedidoGateway;

    public PedidoServiceImpl( PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public PedidoDTO buscarPedidoPorId(String idPedido) {
        return pedidoGateway.buscarPedidoPorId(idPedido);
    }

}
