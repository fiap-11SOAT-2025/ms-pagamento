package org.fiap.Pagamento.core.usercases;

import org.fiap.Pagamento.presentation.dto.producao.StatusPedidoDTO;

public interface ProducaoUseCases {

    void criaStatusRecebidoPedido(StatusPedidoDTO statusPedidoDTO);
}
