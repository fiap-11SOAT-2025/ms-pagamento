package org.fiap.Pagamento.infrastructure.gateways.client;

import org.fiap.Pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.fiap.Pagamento.core.gateways.ProducaoGateway;
import org.fiap.Pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProducaoClient implements ProducaoGateway {

    private final WebClient webClient;

    @Value("${api.producao.host}")
    String hostApiproducao;

    public ProducaoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(hostApiproducao).build();
    }

    @Override
    public void criaStatusRecebidoPedido(StatusPedidoDTO statusPedidoDTO) {

        String endpoint = "/producao/status-pedido";

        try{

            webClient.post()
                    .uri(endpoint)
                    .bodyValue(statusPedidoDTO)
                    .exchangeToMono(response -> {
                        if (response.statusCode().is4xxClientError()) {
                            return response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RecursoNaoEncontradoExcecao(
                                            "StatusPedido não encontrado com o pedidoId: " + statusPedidoDTO.pedidoId() + ". Detalhes: " + body
                                    )));
                        } else if (response.statusCode().is5xxServerError()) {
                            return response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException(
                                            "Erro interno no serviço de produção: " + body
                                    )));
                        } else {
                            return Mono.error(new RuntimeException(
                                    "Resposta inesperada do serviço de produção: " + response.statusCode()
                            ));
                        }
                    })
                    .block();

        } catch (RecursoNaoEncontradoExcecao e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar statusPedido com o pedidoId: " + statusPedidoDTO.pedidoId(), e);
        }
    }
}
