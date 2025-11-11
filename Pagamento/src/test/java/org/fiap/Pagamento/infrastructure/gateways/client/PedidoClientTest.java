package org.fiap.Pagamento.infrastructure.gateways.client;

import org.fiap.Pagamento.presentation.dto.pedido.PedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PedidoClientTest {

    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.Builder webClientBuilderMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    private PedidoClient pedidoClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock do builder
        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientMock);

        // Cria o client manualmente sem depender do @Value
        pedidoClient = new PedidoClient(webClientBuilderMock,"http://fake-api-pedido.com");

    }

    @Test
    void deveBuscarPedidoPorIdComSucesso() {
        String pedidoId = "12345";

        PedidoDTO pedidoEsperado = new PedidoDTO(
                pedidoId,
                List.of(),
                "CRIADO",
                "cliente123",
                BigDecimal.valueOf(120.50)
        );

        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/pedidos/" + pedidoId)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.header(eq(HttpHeaders.CONTENT_TYPE), eq(MediaType.APPLICATION_JSON_VALUE)))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(PedidoDTO.class)).thenReturn(Mono.just(pedidoEsperado));

        PedidoDTO resultado = pedidoClient.buscarPedidoPorId(pedidoId);

        assertNotNull(resultado);
        assertEquals(pedidoEsperado.id(), resultado.id());
        assertEquals(pedidoEsperado.status(), resultado.status());
        assertEquals(pedidoEsperado.clienteId(), resultado.clienteId());

        verify(webClientMock).get();
        verify(requestHeadersUriSpecMock).uri("/pedidos/" + pedidoId);
        verify(requestHeadersSpecMock).header(eq(HttpHeaders.CONTENT_TYPE), eq(MediaType.APPLICATION_JSON_VALUE));
    }
}
