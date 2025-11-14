package org.fiap.ms_pagamento.core.gateways;


import org.fiap.ms_pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.StatusMerchantOrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public interface MercadoPagoGateway {

    MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO dto, PedidoDTO pedido);

    StatusMerchantOrderMercadoPagoDTO statusMerchantOrder(String merchantOrderId);
}
