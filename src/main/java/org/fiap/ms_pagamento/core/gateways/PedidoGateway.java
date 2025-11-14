package org.fiap.ms_pagamento.core.gateways;

import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public interface PedidoGateway {

   PedidoDTO buscarPedidoPorId(String PedidoId);
}
