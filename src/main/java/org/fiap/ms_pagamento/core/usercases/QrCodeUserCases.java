package org.fiap.ms_pagamento.core.usercases;

public interface QrCodeUserCases {

    byte[] geraQrCodePagamentoMercadoPago(String codigoEmvCo);
}
