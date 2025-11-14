package org.fiap.ms_pagamento.infrastructure.gateways.client;

import org.fiap.ms_pagamento.presentation.dto.mercadopago.*;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
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

class MercadoPagoClientTest {

    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.Builder webClientBuilderMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;
    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    private MercadoPagoClient mercadoPagoClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do builder mock
        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientMock);

        // Agora sim podemos criar o client sem NullPointer
        mercadoPagoClient = new MercadoPagoClient(webClientBuilderMock);
        mercadoPagoClient.token = "fake-token";
        mercadoPagoClient.userId = "12345";
        mercadoPagoClient.externalPosId = "POS01";
    }

    @Test
    void deveGerarQrCodePagamentoComSucesso() {
        OrderMercadoPagoDTO orderDto = mock(OrderMercadoPagoDTO.class);
        PedidoDTO pedidoDto = new PedidoDTO("123e4567-e89b-12d3-a456-426614174000", null, null, null, null);

        PaymentsMercadoPagoOrderResponseDTO payment = new PaymentsMercadoPagoOrderResponseDTO(
                "payment-1",
                "100.00",
                "approved",
                "accredited"
        );

        TransactionsMercadoPagoOrderResponseDTO transactions = new TransactionsMercadoPagoOrderResponseDTO(
                List.of(payment)
        );

        IntegrationDataMercadoPagoOrderResponseDTO integrationData =
                new IntegrationDataMercadoPagoOrderResponseDTO("APP-123");

        QrMercadoPagoOrderDTO qrConfig = new QrMercadoPagoOrderDTO("POS01", "dynamic");
        ConfigMercadoPagoOrderDTO config = new ConfigMercadoPagoOrderDTO(qrConfig);

        ItemsMercadoPagoOrderDTO item = new ItemsMercadoPagoOrderDTO("Produto Teste", 2, BigDecimal.valueOf(50));

        TypeResponseMercadoPagoOrderResponseDTO typeResponse = new TypeResponseMercadoPagoOrderResponseDTO("QR-CODE-DATA");

        MercadoPagoOrderResponseDTO responseEsperado = new MercadoPagoOrderResponseDTO(
                "order-1",
                "order",
                "automatic",
                "REF-123",
                "100.00",
                "2025-12-01T00:00:00Z",
                "BR",
                "999",
                "open",
                "ok",
                "BRL",
                "2025-11-11T10:00:00Z",
                "2025-11-11T10:00:00Z",
                integrationData,
                transactions,
                config,
                List.of(item),
                typeResponse
        );

        // Configura mocks do WebClient
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.header(anyString(), anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.bodyValue(any())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(MercadoPagoOrderResponseDTO.class)).thenReturn(Mono.just(responseEsperado));

        // Chamada do método
        MercadoPagoOrderResponseDTO resultado = mercadoPagoClient.geraQrCodePagamento(orderDto, pedidoDto);

        // Asserts para todos os campos
        assertNotNull(resultado);
        assertEquals("order-1", resultado.id());
        assertEquals("order", resultado.type());
        assertEquals("APP-123", resultado.integration_data().application_id());
        assertEquals(1, resultado.transactions().payments().size());
        assertEquals("payment-1", resultado.transactions().payments().get(0).id());
        assertEquals("Produto Teste", resultado.items().get(0).title());
        assertEquals("QR-CODE-DATA", resultado.type_response().qr_data());
        assertEquals("POS01", resultado.config().qr().external_pos_id());

        // Verificações de chamadas
        verify(webClientMock).post();
        verify(requestBodyUriSpecMock).uri("/v1/orders");
        verify(requestBodySpecMock).header(eq("Authorization"), contains("Bearer"));
        verify(requestBodySpecMock).header(eq(HttpHeaders.CONTENT_TYPE), eq(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void deveBuscarStatusMerchantOrderComSucesso() {
        String merchantOrderId = "123456789";

        // Cria objetos de pagamento
        PaymentsMerchantOrderMercadoPagoDTO payment = new PaymentsMerchantOrderMercadoPagoDTO(
                1L,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(10),
                "BRL",
                "approved",
                "accredited",
                "regular",
                "2025-11-10T10:00:00Z",
                "2025-11-10T09:00:00Z",
                "2025-11-10T09:30:00Z",
                BigDecimal.ZERO
        );

        // Cria o collector
        CollectorMerchantOrderMercadoPagoDTO collector = new CollectorMerchantOrderMercadoPagoDTO(
                1L,
                "collector@example.com",
                "CollectorNick"
        );

        // Cria o payer
        PayerMerchantOrderMercadoPagoDTO payer = new PayerMerchantOrderMercadoPagoDTO(
                1L,
                "payer@example.com"
        );

        // Cria itens
        ItemsMerchantOrderMercadoPagoDTO item = new ItemsMerchantOrderMercadoPagoDTO(
                "item-1",
                "categoria-1",
                "BRL",
                "Descrição do item",
                "http://imagem.com/item.jpg",
                "Item 1",
                2L,
                BigDecimal.valueOf(50)
        );

        StatusMerchantOrderMercadoPagoDTO statusEsperado = new StatusMerchantOrderMercadoPagoDTO(
                1L,
                "paid",
                "REF-123",
                "PREF-456",
                List.of(payment),
                collector,
                "NONE",
                "http://callback",
                "2025-11-10T00:00:00Z",
                "2025-11-11T00:00:00Z",
                "SPONSOR-123",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(100),
                "MLB",
                BigDecimal.valueOf(100),
                BigDecimal.ZERO,
                payer,
                List.of(item),
                false,
                "additional info",
                "APP-123",
                "closed"
        );


        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.header(anyString(), anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(StatusMerchantOrderMercadoPagoDTO.class)).thenReturn(Mono.just(statusEsperado));

        StatusMerchantOrderMercadoPagoDTO resultado = mercadoPagoClient.statusMerchantOrder(merchantOrderId);

        assertNotNull(resultado);
        assertEquals("paid", resultado.status());
        verify(webClientMock).get();
        verify(requestHeadersUriSpecMock).uri("/merchant_orders/" + merchantOrderId);
        verify(requestHeadersSpecMock).header(eq("Authorization"), contains("Bearer"));
    }
}
