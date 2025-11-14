package org.fiap.ms_pagamento.presentation.dto.pedido;

import java.math.BigDecimal;

public record ItemPedidoDTO(String produto, Integer quantidade, BigDecimal preco) {
}
