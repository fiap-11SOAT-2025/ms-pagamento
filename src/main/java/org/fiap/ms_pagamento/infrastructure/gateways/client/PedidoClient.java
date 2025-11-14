package org.fiap.ms_pagamento.infrastructure.gateways.client;

import org.fiap.ms_pagamento.core.gateways.PedidoGateway;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PedidoClient implements PedidoGateway {

    private final WebClient webClient;

    @Autowired
    public PedidoClient(WebClient.Builder webClientBuilder, @Value("${api.pedido.host}") String hostApiPedido) {
        this.webClient = webClientBuilder.baseUrl(hostApiPedido).build();
    }


    @Override
    public PedidoDTO buscarPedidoPorId(String pedidoId) {

        String endpoint = "/pedidos/" + pedidoId;

        return webClient.get()
                .uri(endpoint)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(PedidoDTO.class)
                .block();
    }
}
