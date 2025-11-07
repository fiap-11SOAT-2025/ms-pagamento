package org.fiap.Pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo configuração do tipo de Order no mercado pago para integração de pedidos.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param qr Configurações da order QR.
*/
public record ConfigMercadoPagoOrderDTO(
        QrMercadoPagoOrderDTO qr
) {
}
