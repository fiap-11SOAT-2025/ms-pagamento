package org.fiap.ms_pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo informações sobre a aplicação do Mercado Pago que criou a order..
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param application_id Identificador da aplicação do Mercado Pago que criou a order.
 */
public record IntegrationDataMercadoPagoOrderResponseDTO(
        String application_id
) {
}
