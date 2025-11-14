package org.fiap.ms_pagamento.presentation.dto.mercadopago;


import java.math.BigDecimal;

/**
 * DTO representando um item no pagamento.
 * <p>
 * Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 * @param title Nome do item. O limite de caracteres é 150.
 * @param unit_price Preço unitário do item comprado. Deve ser enviado com duas casas decimais.
 * @param quantity Quantidade de itens comprados.
 * @param unit_measure Um valor que representa uma unidade de medida associada ao item. Pode conter no máximo 10 caracteres e sugerimos que envie o valor em letras minúsculas.
 */
public record ItemsMercadoPagoOrderDTO(
        String title,
        String unit_price,
        Integer quantity,
        String unit_measure
) {
    public ItemsMercadoPagoOrderDTO (String produto, Integer quantidade, BigDecimal preco) {
        this(produto, preco.toString(), quantidade,"unit");
    }
}
