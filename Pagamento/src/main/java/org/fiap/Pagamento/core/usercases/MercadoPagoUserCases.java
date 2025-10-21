package org.fiap.Pagamento.core.usercases;


import org.fiap.Pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface MercadoPagoUserCases {

    OrderMercadoPagoDTO geraOrderMercadoPago(Pedido pedido, BigDecimal valorTotal);

    MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO orderMercadoPagoDTO, Pedido pedido);
}
