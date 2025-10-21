package org.fiap.Pagamento.presentation.dto.mercadopago;

/**
 * DTO contendo informações de pagamento de um produto ou serviço com a identificação do pedido de sua escolha.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/merchant_orders/_merchant_orders_id/get">...</a>
 *
 *  @param id Identificador da conta coletora do Mercado Pago
 *  @param email E-mail associado à conta Mercado Pago do coletor
 *  @param nickname Nick do collector
 */
public record CollectorMerchantOrderMercadoPagoDTO(
        Long id,
        String email,
        String nickname
) {
}
