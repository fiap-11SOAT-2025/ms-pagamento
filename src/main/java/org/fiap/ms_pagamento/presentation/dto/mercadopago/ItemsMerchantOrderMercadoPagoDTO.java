package org.fiap.ms_pagamento.presentation.dto.mercadopago;

import java.math.BigDecimal;

/**
 * DTO contendo Atributos dos itens do pedido.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/merchant_orders/_merchant_orders_id/get">...</a>
 *
 *  @param id Indentificador do item
 *  @param category_id Esta é uma string livre onde a categoria do item pode ser adicionada.
 *  @param currency_id Identificador da moeda utilizada no pagamento
 *  @param description Descrição do artigo
 *  @param picture_url URL da imagem do anúncio
 *  @param title Título do item, é apresentado o fluxo de pagamento
 *  @param quantity Quantidade do produto
 *  @param unit_price Preço unitário do item
 *
 */
public record ItemsMerchantOrderMercadoPagoDTO(
        String id,
        String category_id,
        String currency_id,
        String description,
        String picture_url,
        String title,
        Long quantity,
        BigDecimal unit_price
) {
}
