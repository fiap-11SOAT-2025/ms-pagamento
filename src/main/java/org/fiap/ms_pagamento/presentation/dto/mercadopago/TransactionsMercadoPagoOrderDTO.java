package org.fiap.ms_pagamento.presentation.dto.mercadopago;

import java.util.List;

/**
 * DTO contendo informações sobre a transação associada à order. Quando o "type" for "qr", podem ser incluídas transações de pagamento ("payments").
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param  payments Contém informações sobre a order do pagamento. Somente uma transação de pagamento pode ser enviada por order.
*/
public record TransactionsMercadoPagoOrderDTO(
        List<PaymentsMercadoPagoOrderDTO> payments
) {

}
