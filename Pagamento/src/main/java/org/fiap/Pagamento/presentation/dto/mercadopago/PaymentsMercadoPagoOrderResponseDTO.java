package org.fiap.Pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo informações sobre o pagamento associado à order.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param  id Identificador da transação de pagamento criada na requisição, gerado automaticamente pelo Mercado Pago.
 *  @param  amount Valor do pagamento, atribuído ao momento da criação da order.
 *  @param  status Status atual do pagamento.
 *  @param  status_detail Detalhes sobre o status atual do pagamento.
 */
public record PaymentsMercadoPagoOrderResponseDTO(
        String id,
        String amount,
        String status,
        String status_detail
) {
}
