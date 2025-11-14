package org.fiap.ms_pagamento.core.gateways;

import org.fiap.ms_pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.springframework.stereotype.Component;

@Component
public interface ProducaoGateway {

    void criaStatusRecebidoPedido(StatusPedidoDTO statusPedidoDTO);
}
