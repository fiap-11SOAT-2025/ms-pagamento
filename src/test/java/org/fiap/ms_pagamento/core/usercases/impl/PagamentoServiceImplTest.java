package org.fiap.ms_pagamento.core.usercases.impl;

import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.enums.StatusPagamentoEnum;
import org.fiap.ms_pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.fiap.ms_pagamento.core.gateways.PagamentoGateway;
import org.fiap.ms_pagamento.core.usercases.*;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.TypeResponseMercadoPagoOrderResponseDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.ItemPedidoDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.fiap.ms_pagamento.presentation.dto.producao.StatusPedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoServiceImplTest {

    @Mock
    private PagamentoGateway pagamentoGateway;
    @Mock
    private QrCodeUserCases qrCodeUserCases;
    @Mock
    private PedidoUseCases pedidoUseCases;
    @Mock
    private MercadoPagoUserCases mercadoPagoUserCases;
    @Mock
    private ProducaoUseCases producaoUseCases;

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    private PedidoDTO pedidoDTO;
    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoDTO = new PedidoDTO(
                "PED001",
                List.of(new ItemPedidoDTO("Produto", 1, new BigDecimal("10.00"))),
                "NOVO",
                "CLI123",
                new BigDecimal("10.00")
        );

        pagamento = new Pagamento(1L, "PED001", null, null, StatusPagamentoEnum.PENDENTE_QRCODE.getCodigo());
    }

    // -----------------------------------------
    // TESTE: geraQrCodePagamentoMercadoPago()
    // -----------------------------------------
    @Test
    void deveGerarQrCodePagamentoMercadoPagoComSucesso() {
        // Arrange
        TypeResponseMercadoPagoOrderResponseDTO typeResponse = new TypeResponseMercadoPagoOrderResponseDTO("qrData123");
        MercadoPagoOrderResponseDTO response = new MercadoPagoOrderResponseDTO(
                "id", "type", "automatic", "ext123", "10.00",
                "2025-11-05", "BR", "user", "open", "detail",
                "BRL", "data", "update", null, null, null, List.of(), typeResponse
        );

        when(pedidoUseCases.buscarPedidoPorId("PED001")).thenReturn(pedidoDTO);
        when(mercadoPagoUserCases.geraOrderMercadoPago(eq(pedidoDTO), any(BigDecimal.class))).thenReturn(mock(OrderMercadoPagoDTO.class));
        when(mercadoPagoUserCases.geraQrCodePagamento(any(), eq(pedidoDTO))).thenReturn(response);
        when(pagamentoGateway.findByPedidoId("PED001")).thenReturn(Optional.of(pagamento));
        when(pagamentoGateway.save(any())).thenReturn(Optional.of(pagamento));

        // Act
        Pagamento resultado = pagamentoService.geraQrCodePagamentoMercadoPago("PED001");

        // Assert
        assertEquals("qrData123", resultado.getQrCodeMercadoPago());
        assertEquals("ext123", resultado.getExternalReferenceMercadoPago());
        assertEquals(StatusPagamentoEnum.PENDENTE_PAGAMENTO.getCodigo(), resultado.getStatusPagamentoId());
        verify(pagamentoGateway, times(1)).save(any(Pagamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoExistirAoGerarQrCode() {
        // Arrange
        when(pedidoUseCases.buscarPedidoPorId("PED001")).thenReturn(pedidoDTO);
        when(mercadoPagoUserCases.geraOrderMercadoPago(any(), any())).thenReturn(mock(OrderMercadoPagoDTO.class));
        when(mercadoPagoUserCases.geraQrCodePagamento(any(), any())).thenReturn(mock(MercadoPagoOrderResponseDTO.class));
        when(pagamentoGateway.findByPedidoId("PED001")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RecursoNaoEncontradoExcecao.class, () ->
                pagamentoService.geraQrCodePagamentoMercadoPago("PED001")
        );
    }

    // -----------------------------------------
    // TESTE: vizualizarQrCodePagamentoMercadoPago()
    // -----------------------------------------
    @Test
    void deveVisualizarQrCodeComSucesso() {
        // Arrange
        pagamento.setQrCodeMercadoPago("dadosQR");
        when(pagamentoGateway.findByPedidoId("PED001")).thenReturn(Optional.of(pagamento));
        when(qrCodeUserCases.geraQrCodePagamentoMercadoPago("dadosQR")).thenReturn("imagemQR".getBytes());

        // Act
        byte[] resultado = pagamentoService.vizualizarQrCodePagamentoMercadoPago("PED001");

        // Assert
        assertArrayEquals("imagemQR".getBytes(), resultado);
        verify(qrCodeUserCases, times(1)).geraQrCodePagamentoMercadoPago("dadosQR");
    }

    // -----------------------------------------
    // TESTE: buscaPagamentoPorPedidoId()
    // -----------------------------------------
    @Test
    void deveBuscarPagamentoPorPedidoId() {
        when(pagamentoGateway.findByPedidoId("PED001")).thenReturn(Optional.of(pagamento));

        Pagamento resultado = pagamentoService.buscaPagamentoPorPedidoId("PED001");

        assertEquals("PED001", resultado.getPedidoId());
        verify(pagamentoGateway, times(1)).findByPedidoId("PED001");
    }

    @Test
    void deveLancarExcecaoAoBuscarPagamentoInexistentePorPedidoId() {
        when(pagamentoGateway.findByPedidoId("PED001")).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoExcecao.class, () ->
                pagamentoService.buscaPagamentoPorPedidoId("PED001")
        );
    }

    // -----------------------------------------
    // TESTE: buscaPagamentoPorExternalReference()
    // -----------------------------------------
    @Test
    void deveBuscarPagamentoPorExternalReference() {
        when(pagamentoGateway.findByexternalReferenceMercadoPago("ext123")).thenReturn(Optional.of(pagamento));

        Pagamento resultado = pagamentoService.buscaPagamentoPorExternalReference("ext123");

        assertEquals(pagamento, resultado);
        verify(pagamentoGateway, times(1)).findByexternalReferenceMercadoPago("ext123");
    }

    @Test
    void deveLancarExcecaoAoBuscarPagamentoPorExternalReferenceInexistente() {
        when(pagamentoGateway.findByexternalReferenceMercadoPago("ext123")).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoExcecao.class, () ->
                pagamentoService.buscaPagamentoPorExternalReference("ext123")
        );
    }

    // -----------------------------------------
    // TESTE: atualizaPagamentoPedido()
    // -----------------------------------------
    @Test
    void deveAtualizarPagamentoEPedirProducao() {
        doNothing().when(producaoUseCases).criaStatusRecebidoPedido(any(StatusPedidoDTO.class));
        when(pagamentoGateway.save(any())).thenReturn(Optional.of(pagamento));

        pagamentoService.atualizaPagamentoPedido(pagamento);

        assertEquals(StatusPagamentoEnum.PAGO.getCodigo(), pagamento.getStatusPagamentoId());
        verify(producaoUseCases, times(1)).criaStatusRecebidoPedido(any(StatusPedidoDTO.class));
        verify(pagamentoGateway, times(1)).save(pagamento);
    }

    // -----------------------------------------
    // TESTE: buscaPagamentos()
    // -----------------------------------------
    @Test
    void deveBuscarTodosPagamentos() {
        when(pagamentoGateway.findAll()).thenReturn(List.of(pagamento));

        List<Pagamento> resultado = pagamentoService.buscaPagamentos();

        assertEquals(1, resultado.size());
        assertEquals("PED001", resultado.get(0).getPedidoId());
    }
}
