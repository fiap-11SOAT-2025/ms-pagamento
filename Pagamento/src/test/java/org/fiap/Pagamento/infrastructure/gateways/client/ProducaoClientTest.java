package org.fiap.Pagamento.infrastructure.gateways.client;

import org.fiap.Pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.fiap.Pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProducaoClientTest {

    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.Builder webClientBuilderMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;
    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;
    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec requestHeadersSpecMock; // sem tipo genérico
    @Mock
    private ClientResponse clientResponseMock;

    private ProducaoClient producaoClient;
    private final String hostFake = "http://fake-producao.com";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientMock);

        // Post request mocks
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.bodyValue(any())).thenReturn(requestHeadersSpecMock); // agora sem conflito de generics

        producaoClient = new ProducaoClient(webClientBuilderMock, hostFake);
    }

    @Test
    void deveLancarRecursoNaoEncontradoQuando4xx() {
        StatusPedidoDTO statusDTO = new StatusPedidoDTO("pedido123");

        when(requestHeadersSpecMock.exchangeToMono(any()))
                .thenAnswer(invocation -> {
                    java.util.function.Function<ClientResponse, Mono<?>> func = invocation.getArgument(0);
                    when(clientResponseMock.statusCode()).thenReturn(org.springframework.http.HttpStatus.NOT_FOUND);
                    when(clientResponseMock.bodyToMono(String.class)).thenReturn(Mono.just("Erro 404"));
                    return func.apply(clientResponseMock);
                });

        assertThrows(RecursoNaoEncontradoExcecao.class,
                () -> producaoClient.criaStatusRecebidoPedido(statusDTO));
    }

    @Test
    void deveLancarRuntimeExceptionQuando5xx() {
        StatusPedidoDTO statusDTO = new StatusPedidoDTO("pedido456");

        when(requestHeadersSpecMock.exchangeToMono(any()))
                .thenAnswer(invocation -> {
                    java.util.function.Function<ClientResponse, Mono<?>> func = invocation.getArgument(0);
                    when(clientResponseMock.statusCode()).thenReturn(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
                    when(clientResponseMock.bodyToMono(String.class)).thenReturn(Mono.just("Erro 500"));
                    return func.apply(clientResponseMock);
                });

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> producaoClient.criaStatusRecebidoPedido(statusDTO));

        assertTrue(ex.getMessage().contains("Erro ao buscar statusPedido com o pedidoId: pedido456"));
    }

    @Test
    void deveLancarRuntimeExceptionParaRespostaInesperada() {
        StatusPedidoDTO statusDTO = new StatusPedidoDTO("pedido789");

        when(requestHeadersSpecMock.exchangeToMono(any()))
                .thenAnswer(invocation -> {
                    java.util.function.Function<ClientResponse, Mono<?>> func = invocation.getArgument(0);
                    when(clientResponseMock.statusCode()).thenReturn(org.springframework.http.HttpStatus.CONTINUE); // 100
                    return func.apply(clientResponseMock);
                });

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> producaoClient.criaStatusRecebidoPedido(statusDTO));

        assertTrue(ex.getMessage().contains("Erro ao buscar statusPedido com o pedidoId: pedido789"));
    }
}
