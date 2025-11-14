package org.fiap.ms_pagamento.presentation.dto.mercadopago;

import java.util.List;

/**
 * DTO contendo response da Order do Mercado Pago..
 * <p>
 *Documentação do mercado pago, contendo os parâmetros necessarios para a requisição :
 * <a href="https://www.mercadopago.com.br/developers/pt/reference/in-person-payments/qr-code/orders/create-order/post">...</a>
 *
 *  @param id Identificador da order criada na requisição, gerado automaticamente pelo Mercado Pago.
 *  @param type Tipo de order.
 *  @param processing_mode Indica como o order será processada. Para orders QR, o único valor permitido será "automatic", e indica que o order está pronta para ser processada.
 *  @param external_reference Referência externa da order, atribuída no momento da criação.
 *  @param total_amount Valor total da order, atribuído no momento da criação. Representa a soma das transações associadas à order.
 *  @param expiration_time Valor atribuído na criação para a expiração da order no formato ISO 8601
 *  @param country_code Identificador do site ou local de onde a order foi criada
 *  @param user_id ID do usuário do Mercado Pago que criou a order.
 *  @param status Status atual da order.
 *  @param status_detail Detalhes sobre o status atual da order.
 *  @param currency
 *  @param created_date Data de criação da order, no formato "yyyy-MM-ddTHH:mm:ss.sssZ".
 *  @param last_updated_date Data da última atualização da order, no formato "yyyy-MM-ddTHH:mm:ss.sssZ".
 *  @param integration_data Contém informações sobre a aplicação do Mercado Pago que criou a order.
 *  @param transactions Contém informações sobre as transações associadas a uma order.
 *  @param config Configuração do tipo de order.
 *  @param items Informações sobre a lista de itens a serem pagos.
 *  @param type_response Objeto retornado se o campo "config.qr.mode" foi definido como "dynamic" ou "hybrid".
 */
public record MercadoPagoOrderResponseDTO(
        String id,
        String type,
        String processing_mode,
        String external_reference,
        String total_amount,
        String expiration_time,
        String country_code,
        String user_id,
        String status,
        String status_detail,
        String currency,
        String created_date,
        String last_updated_date,
        IntegrationDataMercadoPagoOrderResponseDTO  integration_data,
        TransactionsMercadoPagoOrderResponseDTO transactions,
        ConfigMercadoPagoOrderDTO config,
        List<ItemsMercadoPagoOrderDTO> items,
        TypeResponseMercadoPagoOrderResponseDTO type_response
) {

}
