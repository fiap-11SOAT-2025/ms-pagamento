package org.fiap.Pagamento.core.usercases.impl;

import org.fiap.Pagamento.core.gateways.PedidoGateway;
import org.fiap.Pagamento.core.usercases.PedidoUseCases;
import org.fiap.Pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
