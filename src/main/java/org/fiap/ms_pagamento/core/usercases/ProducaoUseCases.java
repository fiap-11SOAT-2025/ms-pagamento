package org.fiap.ms_pagamento.core.usercases;

import org.fiap.ms_pagamento.presentation.dto.producao.StatusPedidoDTO;

public interface ProducaoUseCases {

    void criaStatusRecebidoPedido(StatusPedidoDTO statusPedidoDTO);
}
