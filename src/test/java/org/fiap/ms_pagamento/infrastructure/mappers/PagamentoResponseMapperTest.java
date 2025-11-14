package org.fiap.ms_pagamento.infrastructure.mappers;

import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.enums.StatusPagamentoEnum;
import org.fiap.ms_pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.fiap.ms_pagamento.infrastructure.persistence.PagamentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoResponseMapperTest {

    private PagamentoResponseMapper mapper;

    @BeforeEach
    void setUp() {
        // Cria um mock do mapper para poder testar apenas os métodos default
        mapper = Mockito.mock(PagamentoResponseMapper.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    void deveConverterEntityParaDomainCorretamente() {
        PagamentoEntity entity = new PagamentoEntity();
        entity.setId(1L);
        entity.setPedidoId("100");
        entity.setQrCodeMercadoPago("QR123");
        entity.setExternalReferenceMercadoPago("REF123");
        entity.setStatusPagamentoId(2);

        Pagamento domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("100", domain.getPedidoId());
        assertEquals("QR123", domain.getQrCodeMercadoPago());
        assertEquals("REF123", domain.getExternalReferenceMercadoPago());
        assertEquals(2, domain.getStatusPagamentoId());
    }

    @Test
    void deveRetornarNullQuandoEntityForNull() {
        Pagamento domain = mapper.toDomain(null);
        assertNull(domain);
    }

    @Test
    void deveMapearStatusPagamentoCorretamente() {
        String result = mapper.mapStatusPagamento(StatusPagamentoEnum.PAGO.getCodigo());
        assertEquals("PAGO", result);
    }

    @Test
    void deveRetornarNullQuandoStatusPagamentoIdForNull() {
        assertNull(mapper.mapStatusPagamento(null));
    }

    @Test
    void deveLancarExcecaoQuandoStatusPagamentoIdInvalido() {
        Exception exception = assertThrows(RecursoNaoEncontradoExcecao.class, () -> {
            mapper.mapStatusPagamento(999); // código inexistente no enum
        });
        assertTrue(exception.getMessage().toLowerCase().contains("inválido"));
    }
}
