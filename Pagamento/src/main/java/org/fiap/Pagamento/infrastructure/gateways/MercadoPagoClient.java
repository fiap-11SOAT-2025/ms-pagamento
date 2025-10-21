package org.fiap.Pagamento.infrastructure.gateways;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.StatusMerchantOrderMercadoPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MercadoPagoClient implements MercadoPagoGateway {

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    private final WebClient webClient;

    @Value("${api.mercadopago.userId}")
    String userId;
    @Value("${api.mercadopago.externalPosId}")
    String externalPosId;
    @Value("${api.mercadopago.token}")
    String token;

    public MercadoPagoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.mercadopago.com").build();
    }

    @Override
    public MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO orderMercadoPagoDTO, Pedido pedido)  {

        String endpoint = "/v1/orders";
        String keyPedido= "MCPG00"+ String.format("%04d", pedido.getId());

        return webClient.post()
                .uri(endpoint)
                .header("Authorization", "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("X-Idempotency-Key", keyPedido)
                .bodyValue(orderMercadoPagoDTO)
                .retrieve()
                .bodyToMono(MercadoPagoOrderResponseDTO.class)
                .block();
    }

    @Override
    public StatusMerchantOrderMercadoPagoDTO statusMerchantOrder(String merchantOrderId){

        String endpoint = "/merchant_orders/" + merchantOrderId;

        return webClient.get()
                .uri(endpoint)
                .header("Authorization", "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(StatusMerchantOrderMercadoPagoDTO.class)
                .block();
    }


}
