package org.fiap.Pagamento.presentation.dto.mercadopago;

import java.math.BigDecimal;

/**
 * DTO contendo atributos de pagamento associados ao pedido.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/merchant_orders/_merchant_orders_id/get">...</a>
 *
 *  @param id Identificador único de pagamento, gerado automaticamente pelo Mercado Pago
 *  @param transaction_amount Valor total da tentativa de pagamento. É a soma de dinheiro que é depositada ao vendedor. Campo numérico.
 *  @param total_paid_amount Valor total cobrado ao pagador
 *  @param shipping_cost Custo de envio, se aplicável
 *  @param currency_id Identificador da moeda utilizada no pagamento
 *  @param status Estado do pagamento
 *  @param status_detail Detalhe em que resultou a Coleção
 *  @param operation_type data_type da operação
 *  @param date_approved Data de aprovação do pagamento
 *  @param date_created Data de criação do pagamento
 *  @param last_modified Data da última atualização do pedido. O formato é YYYY-MM-DDThh:mm:ss.dcm-GMT, por exemplo 2021-12-29T20:19:17.000-04:00.
 *  @param amount_refunded Valor reembolsado. Não pode ser um valor maior que total_amount.
 */
public record PaymentsMerchantOrderMercadoPagoDTO(
        Long id,
        BigDecimal transaction_amount,
        BigDecimal total_paid_amount,
        BigDecimal shipping_cost,
        String currency_id,
        String status,
        String status_detail,
        String operation_type,
        String date_approved,
        String date_created,
        String last_modified,
        BigDecimal amount_refunded
) {
}
