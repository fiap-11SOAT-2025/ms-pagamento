package org.fiap.ms_pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo informações sobre a order do pagamento. Somente uma transação de pagamento pode ser enviada por order.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param  amount Valor de pagamento. Pode conter duas casas decimais ou nenhuma.
 */
public record PaymentsMercadoPagoOrderDTO(
        String amount
) {
}
