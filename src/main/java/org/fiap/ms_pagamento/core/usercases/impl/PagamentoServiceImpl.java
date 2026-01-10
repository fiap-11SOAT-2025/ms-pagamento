package org.fiap.ms_pagamento.core.usercases.impl;



import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.enums.StatusPagamentoEnum;
import org.fiap.ms_pagamento.core.exception.RecursoNaoEncontradoExcecao;
import org.fiap.ms_pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.ms_pagamento.core.gateways.PagamentoGateway;
import org.fiap.ms_pagamento.core.usercases.*;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.MercadoPagoOrderResponseDTO;
import org.fiap.ms_pagamento.presentation.dto.mercadopago.OrderMercadoPagoDTO;
import org.fiap.ms_pagamento.presentation.dto.pedido.PedidoDTO;
import org.fiap.ms_pagamento.presentation.dto.producao.StatusPedidoDTO;

import java.math.BigDecimal;
import java.util.List;


public class PagamentoServiceImpl implements PagamentoUseCases {

    private final PagamentoGateway pagamentoGateway;
    private MercadoPagoGateway mercadoPagoGateway;
    private QrCodeUserCases qrCodeUserCases;
    private PedidoUseCases pedidoUseCases;
    private MercadoPagoUserCases mercadoPagoUserCases;
    private ProducaoUseCases producaoUseCases;

    public PagamentoServiceImpl(PagamentoGateway pagamentoRepository, QrCodeUserCases qrCodeUserCases,
                                PedidoUseCases pedidoUseCases, MercadoPagoUserCases mercadoPagoUserCases,
                                ProducaoUseCases producaoUseCases) {
        this.pagamentoGateway = pagamentoRepository;
        this.qrCodeUserCases = qrCodeUserCases;
        this.pedidoUseCases = pedidoUseCases;
        this.producaoUseCases = producaoUseCases;
        this.mercadoPagoUserCases = mercadoPagoUserCases;
    }

    @Override
    public Pagamento geraQrCodePagamentoMercadoPago(String idPedido){

        PedidoDTO pedidoDTO = pedidoUseCases.buscarPedidoPorId(idPedido);

        MercadoPagoOrderResponseDTO mercadoPagoOrderResponseDTO = geraPedidoParaPagamentoMercadoPago(pedidoDTO);

        Pagamento pagamento = pagamentoGateway.findByPedidoId(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pagamento não encontrado para o pedido!"));

        pagamento.setQrCodeMercadoPago(mercadoPagoOrderResponseDTO.type_response().qr_data());
        pagamento.setStatusPagamentoId(StatusPagamentoEnum.PENDENTE_PAGAMENTO.getCodigo());
        pagamento.setExternalReferenceMercadoPago(mercadoPagoOrderResponseDTO.external_reference());

        return pagamentoGateway.save(pagamento)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar pagamento!"));
    }

    @Override
    public byte[] vizualizarQrCodePagamentoMercadoPago(String idPedido) {

        Pagamento pagamento = buscaPagamentoPorPedidoId(idPedido);

        return  qrCodeUserCases.geraQrCodePagamentoMercadoPago(pagamento.getQrCodeMercadoPago());
    }

    @Override
    public Pagamento buscaPagamentoPorPedidoId(String idPedido) {
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

        producaoUseCases.criaStatusRecebidoPedido(new StatusPedidoDTO(pagamento.getPedidoId()));

        pagamento.setStatusPagamentoId(StatusPagamentoEnum.PAGO.getCodigo());
        pagamentoGateway.save(pagamento);
    }

    @Override
    public List<Pagamento> buscaPagamentos() {
        return pagamentoGateway.findAll();
    }

    @Override
    public Pagamento geraPagamento(String idPedido) {
        Pagamento pagamento = new Pagamento(idPedido, null,null, StatusPagamentoEnum.PENDENTE_QRCODE.getCodigo());
        return pagamentoGateway.save(pagamento)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar pagamento!"));
    }

    private MercadoPagoOrderResponseDTO geraPedidoParaPagamentoMercadoPago(PedidoDTO pedido){

        BigDecimal valorTotal = pedido.valorTotal();
        OrderMercadoPagoDTO orderMercadoPagoDTO = mercadoPagoUserCases.geraOrderMercadoPago(pedido,valorTotal);

        return mercadoPagoUserCases.geraQrCodePagamento(orderMercadoPagoDTO, pedido);
    }
}
