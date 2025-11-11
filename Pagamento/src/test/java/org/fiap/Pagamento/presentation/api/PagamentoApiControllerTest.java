package org.fiap.Pagamento.presentation.api;

import org.fiap.Pagamento.core.controller.PagamentoController;
import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.infrastructure.mappers.PagamentoResponseMapper;
import org.fiap.Pagamento.presentation.dto.pagamento.PagamentoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoApiControllerTest {

    @Mock
    private PagamentoController pagamentoController;

    @Mock
    private PagamentoResponseMapper mapper;

    @InjectMocks
    private PagamentoApiController pagamentoApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveGerarQrCodePagamentoComSucesso() {
        String idPedido = "123";
        Pagamento pagamento = new Pagamento(1L, "123", "qr123", "ref123", 1);
        PagamentoDTO dto = new PagamentoDTO(1L, "qr123", "ref123", "PENDENTE");

        when(pagamentoController.geraQrCodePagamentoMercadoPago(idPedido)).thenReturn(pagamento);
        when(mapper.entidadeParaDto(pagamento)).thenReturn(dto);

        ResponseEntity<PagamentoDTO> response = pagamentoApiController.gerarQrCodePagamentoMercadoPago(idPedido);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(pagamentoController).geraQrCodePagamentoMercadoPago(idPedido);
        verify(mapper).entidadeParaDto(pagamento);
    }

    @Test
    void deveVisualizarQrCodeComSucesso() {
        String idPedido = "321";
        byte[] qrCode = new byte[]{1, 2, 3};

        when(pagamentoController.vizualizarQrCodePagamentoMercadoPago(idPedido)).thenReturn(qrCode);

        ResponseEntity<byte[]> response = pagamentoApiController.visualizarQrCodePagamentoMercadoPago(idPedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(qrCode, response.getBody());
        assertEquals("image/png", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        verify(pagamentoController).vizualizarQrCodePagamentoMercadoPago(idPedido);
    }

    @Test
    void deveConsultarStatusPagamentoComSucesso() {
        String idPedido = "111";
        Pagamento pagamento = new Pagamento(1L, "111", "qr", "ref", 2);
        PagamentoDTO dto = new PagamentoDTO(1L, "qr", "ref", "PAGO");

        when(pagamentoController.consultaStatusPagamento(idPedido)).thenReturn(pagamento);
        when(mapper.entidadeParaDto(pagamento)).thenReturn(dto);

        ResponseEntity<PagamentoDTO> response = pagamentoApiController.consultaStatusPagamento(idPedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(pagamentoController).consultaStatusPagamento(idPedido);
        verify(mapper).entidadeParaDto(pagamento);
    }

    @Test
    void deveBuscarTodosPagamentosComSucesso() {
        Pagamento pagamento = new Pagamento(1L, "222", "qr", "ref", 3);
        List<Pagamento> pagamentos = List.of(pagamento);
        List<PagamentoDTO> dtos = List.of(new PagamentoDTO(1L, "qr", "ref", "FALHA"));

        when(pagamentoController.buscaPagamentos()).thenReturn(pagamentos);
        when(mapper.entidadesParaDtos(pagamentos)).thenReturn(dtos);

        ResponseEntity<List<PagamentoDTO>> response = pagamentoApiController.buscaPagamentos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
        verify(pagamentoController).buscaPagamentos();
        verify(mapper).entidadesParaDtos(pagamentos);
    }
}
