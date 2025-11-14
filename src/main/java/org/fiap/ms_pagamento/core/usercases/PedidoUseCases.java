package org.fiap.ms_pagamento.core.usercases;

import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;

public interface PedidoUseCases {

    PedidoDTO buscarPedidoPorId(String idPedido);
}
