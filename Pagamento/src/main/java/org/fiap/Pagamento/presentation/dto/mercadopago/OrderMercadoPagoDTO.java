package org.fiap.Pagamento.presentation.dto.mercadopago;



import java.util.List;

/**
 * DTO contendo informações da Order no mercado pago para integração de pedidos.
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param type Título de compra.
 * @param external_reference Tipo de order, associada à solução do Mercado Pago para a qual foi criada. Para pagamentos com Código QR do Mercado Pago, o único valor possível é "qr".
 * @param config Configuração do tipo de order.
 * @param transactions Contém informações sobre a transação associada à order. Quando o "type" for "qr", podem ser incluídas transações de pagamento ("payments").
 * @param items Informações sobre a lista de itens a serem pagos. É possible enviar no máximo 10 itens.
 */
public record OrderMercadoPagoDTO(
        String type,
        String external_reference,
        ConfigMercadoPagoOrderDTO config,
        TransactionsMercadoPagoOrderDTO transactions,
        List<ItemsMercadoPagoOrderDTO> items
) {

    public OrderMercadoPagoDTO(Pedido pedido, ConfigMercadoPagoOrderDTO config, TransactionsMercadoPagoOrderDTO transactions, List<ItemsMercadoPagoOrderDTO> items) {
        this("qr","MCPG00"+ String.format("%04d", pedido.getId()),config,transactions,items);
    }
}
