package org.fiap.ms_pagamento.core.enums;

import org.fiap.ms_pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPagamentoEnumTest {

    @Test
    void deveRetornarEnumCorretoParaCodigoValido() {
        assertEquals(StatusPagamentoEnum.PENDENTE_QRCODE, StatusPagamentoEnum.fromCodigo(1));
        assertEquals(StatusPagamentoEnum.PENDENTE_PAGAMENTO, StatusPagamentoEnum.fromCodigo(2));
        assertEquals(StatusPagamentoEnum.PAGO, StatusPagamentoEnum.fromCodigo(3));
        assertEquals(StatusPagamentoEnum.CANCELADO, StatusPagamentoEnum.fromCodigo(4));
    }

    @Test
    void deveLancarExcecaoParaCodigoInvalido() {
        RecursoNaoEncontradoExcecao exception = assertThrows(
                RecursoNaoEncontradoExcecao.class,
                () -> StatusPagamentoEnum.fromCodigo(99)
        );

        assertEquals("Status inválido: 99", exception.getMessage());
    }

    @Test
    void deveRetornarCodigoCorretoDoEnum() {
        assertEquals(1, StatusPagamentoEnum.PENDENTE_QRCODE.getCodigo());
        assertEquals(2, StatusPagamentoEnum.PENDENTE_PAGAMENTO.getCodigo());
        assertEquals(3, StatusPagamentoEnum.PAGO.getCodigo());
        assertEquals(4, StatusPagamentoEnum.CANCELADO.getCodigo());
    }
}
