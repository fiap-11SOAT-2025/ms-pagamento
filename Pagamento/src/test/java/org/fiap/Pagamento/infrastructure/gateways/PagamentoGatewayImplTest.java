package org.fiap.Pagamento.infrastructure.gateways;

import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.infrastructure.mappers.PagamentoResponseMapper;
import org.fiap.Pagamento.infrastructure.persistence.PagamentoEntity;
import org.fiap.Pagamento.infrastructure.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoGatewayImplTest {

    @Mock
    private PagamentoRepository repository;

    @Mock
    private PagamentoResponseMapper mapper;

    @InjectMocks
    private PagamentoGatewayImpl gateway;

    private Pagamento pagamento;
    private PagamentoEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Cria um Pagamento válido (usando o construtor com todos os campos obrigatórios)
        pagamento = new Pagamento(
                1L,
                "PEDIDO123",
                "QRCodeXYZ",
                "EXT123",
                1
        );

        // Simula a entidade correspondente
        entity = new PagamentoEntity(pagamento);
    }

    @Test
    void deveSalvarPagamentoComSucesso() {
        when(repository.save(any(PagamentoEntity.class))).thenReturn(entity);
        when(mapper.toDomain(any(PagamentoEntity.class))).thenReturn(pagamento);

        Optional<Pagamento> result = gateway.save(pagamento);

        assertTrue(result.isPresent());
        assertEquals(pagamento.getPedidoId(), result.get().getPedidoId());
        verify(repository, times(1)).save(any(PagamentoEntity.class));
        verify(mapper, times(1)).toDomain(any(PagamentoEntity.class));
    }

    @Test
    void deveBuscarPagamentoPorPedidoId() {
        when(repository.findByPedidoId("PEDIDO123")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(pagamento);

        Optional<Pagamento> result = gateway.findByPedidoId("PEDIDO123");

        assertTrue(result.isPresent());
        assertEquals("PEDIDO123", result.get().getPedidoId());
        verify(repository, times(1)).findByPedidoId("PEDIDO123");
        verify(mapper, times(1)).toDomain(entity);
    }

    @Test
    void deveBuscarPagamentoPorExternalReference() {
        when(repository.findByExternalReferenceMercadoPago("EXT123"))
                .thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(pagamento);

        Optional<Pagamento> result = gateway.findByexternalReferenceMercadoPago("EXT123");

        assertTrue(result.isPresent());
        assertEquals("EXT123", result.get().getExternalReferenceMercadoPago());
        verify(repository, times(1)).findByExternalReferenceMercadoPago("EXT123");
        verify(mapper, times(1)).toDomain(entity);
    }

    @Test
    void deveBuscarTodosOsPagamentos() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(pagamento);

        List<Pagamento> result = gateway.findAll();

        assertEquals(1, result.size());
        assertEquals("PEDIDO123", result.get(0).getPedidoId());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDomain(entity);
    }
}
