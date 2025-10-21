package org.fiap.Pagamento.core.usercases;

public interface QrCodeUserCases {

    byte[] geraQrCodePagamentoMercadoPago(String codigoEmvCo);
}
