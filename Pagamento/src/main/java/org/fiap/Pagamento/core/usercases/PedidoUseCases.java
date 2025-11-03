package org.fiap.Pagamento.core.usercases;

import org.fiap.Pagamento.presentation.dto.pedido.PedidoDTO;

public interface PedidoUseCases {

    PedidoDTO buscarPedidoPorId(String idPedido);
}
