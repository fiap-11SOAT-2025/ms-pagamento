package org.fiap.ms_pagamento.core.enums;



import org.fiap.ms_pagamento.core.exception.RecursoNaoEncontradoExcecao;

import java.util.Arrays;

public enum StatusPagamentoEnum {

    PENDENTE_QRCODE(1),
    PENDENTE_PAGAMENTO(2),
    PAGO(3),
    CANCELADO(4);

    private final Integer codigo;

    StatusPagamentoEnum(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public static StatusPagamentoEnum fromCodigo(Integer codigo){
        return Arrays.stream(StatusPagamentoEnum.values()).
                filter(status -> status.getCodigo().equals(codigo)).
                findFirst().
                orElseThrow(() -> new RecursoNaoEncontradoExcecao("Status inválido: "+codigo));
    }
}
