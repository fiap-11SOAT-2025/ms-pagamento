package org.fiap.Pagamento.core.gateways;

import org.fiap.Pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public interface PedidoGateway {

   PedidoDTO buscarPedidoPorId(String PedidoId);
}
