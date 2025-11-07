package org.fiap.Pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo Objeto retornado se o campo "config.qr.mode" foi definido como "dynamic" ou "hybrid".
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param  qr_data Quadro QR, que deve ser transformado em um código QR para que o pagamento possa ser efetuado.
 */
public record TypeResponseMercadoPagoOrderResponseDTO(
        String qr_data
) {
}
