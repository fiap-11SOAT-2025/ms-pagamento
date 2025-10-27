package org.fiap.Pagamento.infrastructure.gateways;

import org.fiap.Pagamento.core.gateways.PedidoGateway;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PedidoClient implements PedidoGateway {

    private final WebClient webClient;

    public PedidoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("").build();
    }
}
