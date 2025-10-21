package org.fiap.Pagamento.presentation.dto.mercadopago;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO contendo informações de pagamento de um produto ou serviço com a identificação do pedido de sua escolha.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/merchant_orders/_merchant_orders_id/get">...</a>
 *
 *  @param id Identificador único do pedido gerado pelo Mercado Pago
 *  @param status Exibe o estado atual da ordem
 *  @param external_reference Identificador único enviado pelo vendedor para relacionar o order_id gerado pelo Mercado Pago ao ID do seu sistema de pagamento
 *  @param preference_id Identificador da preferência de pagamento associada ao pedido
 *  @param payments Atributos de pagamento associados ao pedido
 *  @param collector Atributos da conta do Mercado Pago que cobra o pagamento
 *  @param marketplace Indique se é um pagamento Mercado Livre (MELI) ou Mercado Pago (NONE) Marketplace
 *  @param notification_url URL em que você gostaria de receber uma notificação de pagamento ou merchant_order
 *  @param date_created Data de criação do pedido. O formato é YYYY-MM-DDThh:mm:ss.dcm-GMT, por exemplo 2021-12-29T20:19:17.000-04:00.
 *  @param last_updated Data da última atualização do pedido. O formato é YYYY-MM-DDThh:mm:ss.dcm-GMT, por exemplo 2021-12-29T20:19:17.000-04:00.
 *  @param sponsor_id Identificador da conta Mercado Pago do Partnet. É usado para identificar se a integração foi realizada por um partner.
 *  @param shipping_cost Custo de envio, se aplicável
 *  @param total_amount Valor total da transação na moeda local
 *  @param site_id Site ID.
 *  @param paid_amount Valor pago
 *  @param refunded_amount Valor reembolsado
 *  @param payer Informação do comprador
 *  @param items Atributos dos itens do pedido
 *  @param cancelled Valor booleano que indica se o pedido foi cancelado
 *  @param additional_info Informações adicionais
 *  @param application_id Id do aplicativo
 *  @param order_status Current merchant order status given the payments status
 *
 */
public record StatusMerchantOrderMercadoPagoDTO(
        Long id,
        String status,
        String external_reference,
        String preference_id,
        List<PaymentsMerchantOrderMercadoPagoDTO> payments,
        CollectorMerchantOrderMercadoPagoDTO collector,
        String marketplace,
        String notification_url,
        String date_created,
        String last_updated,
        String sponsor_id,
        BigDecimal shipping_cost,
        BigDecimal total_amount,
        String site_id,
        BigDecimal paid_amount,
        BigDecimal refunded_amount,
        PayerMerchantOrderMercadoPagoDTO payer,
        List<ItemsMerchantOrderMercadoPagoDTO> items,
        Boolean cancelled,
        String additional_info,
        String application_id,
        String order_status
) {
}
