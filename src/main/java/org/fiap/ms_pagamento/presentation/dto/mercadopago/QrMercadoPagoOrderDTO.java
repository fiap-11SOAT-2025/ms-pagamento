package org.fiap.ms_pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo configurações da Order QR no mercado pago para integração de pedidos.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 * @param external_pos_id Identificador externo da caixa, definido pelo integrador durante sua criação. Com sua inclusão, as informações da order ficam associadas à caixa e à loja previamente criadas dentro do sistema Mercado Pago.
 * @param mode Modo de código QR associado à order. Os valores possíveis estão listados abaixo e, se nenhum for enviado, o valor padrão será "static".
 */
public record QrMercadoPagoOrderDTO(
        String external_pos_id,
        String mode
) {
    public QrMercadoPagoOrderDTO(String external_pos_id) {
        this(external_pos_id,"dynamic");
    }
}
