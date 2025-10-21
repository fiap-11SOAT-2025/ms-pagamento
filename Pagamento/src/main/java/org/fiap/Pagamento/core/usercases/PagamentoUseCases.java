package org.fiap.Pagamento.core.usercases;



import org.fiap.Pagamento.core.entities.Pagamento;

import java.util.List;

public interface PagamentoUseCases {

    Pagamento geraQrCodePagamentoMercadoPago(Long idPedido);

    byte[] vizualizarQrCodePagamentoMercadoPago(Long idPedido);

    Pagamento buscaPagamentoPorPedidoId(Long idPedido);

    Pagamento buscaPagamentoPorExternalReference(String externalReference);

    void atualizaPagamentoPedido(Pagamento pagamento);

    List<Pagamento> buscaPagamentos();
}
