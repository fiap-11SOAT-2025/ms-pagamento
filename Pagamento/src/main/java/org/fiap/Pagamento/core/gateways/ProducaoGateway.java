package org.fiap.Pagamento.core.gateways;

import org.fiap.Pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.springframework.stereotype.Component;

@Component
public interface ProducaoGateway {

    void criaStatusRecebidoPedido(StatusPedidoDTO statusPedidoDTO);
}
