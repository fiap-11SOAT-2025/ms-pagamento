package org.fiap.Pagamento.core.gateways;


import org.fiap.Pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.StatusMerchantOrderMercadoPagoDTO;
import org.springframework.stereotype.Component;

@Component
public interface MercadoPagoGateway {

    MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO dto, Pedido pedido);

    StatusMerchantOrderMercadoPagoDTO statusMerchantOrder(String merchantOrderId);
}
