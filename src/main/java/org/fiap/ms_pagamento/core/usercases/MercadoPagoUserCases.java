package org.fiap.ms_pagamento.core.usercases;


import org.fiap.ms_pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface MercadoPagoUserCases {

    OrderMercadoPagoDTO geraOrderMercadoPago(PedidoDTO pedido, BigDecimal valorTotal);

    MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO orderMercadoPagoDTO, PedidoDTO pedido);
}
