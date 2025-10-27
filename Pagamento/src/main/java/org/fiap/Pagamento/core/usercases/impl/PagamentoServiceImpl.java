package org.fiap.Pagamento.core.usercases.impl;



import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.core.enums.StatusPagamentoEnum;
import org.fiap.Pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.core.gateways.PagamentoGateway;
import org.fiap.Pagamento.core.usercases.MercadoPagoUserCases;
import org.fiap.Pagamento.core.usercases.PagamentoUseCases;
import org.fiap.Pagamento.core.usercases.PedidoUseCases;
import org.fiap.Pagamento.core.usercases.QrCodeUserCases;
import org.fiap.Pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.Pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;

import java.math.BigDecimal;
import java.util.List;


public class PagamentoServiceImpl implements PagamentoUseCases {

    private final PagamentoGateway pagamentoGateway;
    private MercadoPagoGateway mercadoPagoGateway;
    private QrCodeUserCases qrCodeUserCases;
    private PedidoUseCases pedidoUseCases;
    private MercadoPagoUserCases mercadoPagoUserCases;

    public PagamentoServiceImpl(PagamentoGateway pagamentoRepository, QrCodeUserCases qrCodeUserCases,
                                PedidoUseCases pedidoUseCases, MercadoPagoUserCases mercadoPagoUserCases) {
        this.pagamentoGateway = pagamentoRepository;
        this.qrCodeUserCases = qrCodeUserCases;
        this.pedidoUseCases = pedidoUseCases;
        this.mercadoPagoUserCases = mercadoPagoUserCases;
    }

    @Override
    public Pagamento geraQrCodePagamentoMercadoPago(Long idPedido){

        Pedido pedido = pedidoUseCases.buscarPedidoPorId(idPedido);

        MercadoPagoOrderResponseDTO mercadoPagoOrderResponseDTO = geraPedidoParaPagamentoMercadoPago(pedido);

        Pagamento pagamento = pagamentoGateway.findByPedidoId(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pagamento não encontrado para o pedido!"));

        pagamento.setQrCodeMercadoPago(mercadoPagoOrderResponseDTO.type_response().qr_data());
        pagamento.setStatusPagamentoId(StatusPagamentoEnum.PENDENTE_PAGAMENTO.getCodigo());
        pagamento.setExternalReferenceMercadoPago(mercadoPagoOrderResponseDTO.external_reference());

        return pagamentoGateway.save(pagamento)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar pagamento!"));
    }

    @Override
    public byte[] vizualizarQrCodePagamentoMercadoPago(Long idPedido) {

        Pagamento pagamento = buscaPagamentoPorPedidoId(idPedido);

        return  qrCodeUserCases.geraQrCodePagamentoMercadoPago(pagamento.getQrCodeMercadoPago());
    }

    @Override
    public Pagamento buscaPagamentoPorPedidoId(Long idPedido) {
        return pagamentoGateway.findByPedidoId(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pagamento não existe!"));
    }

    @Override
    public Pagamento buscaPagamentoPorExternalReference(String externalReference) {
        return pagamentoGateway.findByexternalReferenceMercadoPago(externalReference)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pagamento não existe!"));
    }

    @Override
    public void atualizaPagamentoPedido(Pagamento pagamento){

        Pedido pedido = pedidoUseCases.buscarPedidoPorId(pagamento.getPedido().getId());

        pedido.setStatusPedidoId(StatusPedidoEnum.RECEBIDO.getCodigo());
        pedidoUseCases.salvaPedido(pedido);

        pagamento.setStatusPagamentoId(StatusPagamentoEnum.PAGO.getCodigo());
        pagamentoGateway.save(pagamento);
    }

    @Override
    public List<Pagamento> buscaPagamentos() {
        return pagamentoGateway.findAll();
    }

    private MercadoPagoOrderResponseDTO geraPedidoParaPagamentoMercadoPago(Pedido pedido){

        BigDecimal valorTotal = pedidoUseCases.calcularValorTotalPedido(pedido);
        OrderMercadoPagoDTO orderMercadoPagoDTO = mercadoPagoUserCases.geraOrderMercadoPago(pedido,valorTotal);

        return mercadoPagoUserCases.geraQrCodePagamento(orderMercadoPagoDTO, pedido);
    }
}
