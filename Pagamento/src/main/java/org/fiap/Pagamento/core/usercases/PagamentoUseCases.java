package org.fiap.Pagamento.core.usercases;



import org.fiap.Pagamento.core.entities.Pagamento;

import java.util.List;

public interface PagamentoUseCases {

    Pagamento geraQrCodePagamentoMercadoPago(String idPedido);

    byte[] vizualizarQrCodePagamentoMercadoPago(String idPedido);

    Pagamento buscaPagamentoPorPedidoId(String idPedido);

    Pagamento buscaPagamentoPorExternalReference(String externalReference);

    void atualizaPagamentoPedido(Pagamento pagamento);

    List<Pagamento> buscaPagamentos();
}
