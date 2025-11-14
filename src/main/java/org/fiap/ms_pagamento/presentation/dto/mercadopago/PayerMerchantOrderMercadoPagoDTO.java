package org.fiap.ms_pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo informações do comprador
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/merchant_orders/_merchant_orders_id/get">...</a>
 *
 *  @param id Identificador de pagador
 *  @param email E-mail do pagante
 *
 */
public record PayerMerchantOrderMercadoPagoDTO(
        Long id,
        String email
) {
}
