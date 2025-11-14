package org.fiap.ms_pagamento.presentation.dto.pedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(String id, List<ItemPedidoDTO> itens, String status, String clienteId, BigDecimal valorTotal) {
}
