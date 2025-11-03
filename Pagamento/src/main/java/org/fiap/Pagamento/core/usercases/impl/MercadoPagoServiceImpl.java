package org.fiap.Pagamento.core.usercases.impl;


import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.core.usercases.MercadoPagoUserCases;
import org.fiap.Pagamento.core.usercases.PedidoUseCases;
import org.fiap.Pagamento.presentation.dto.mercadopago.*;
import org.fiap.Pagamento.presentation.dto.pedido.ItemPedidoDTO;
import org.fiap.Pagamento.presentation.dto.pedido.PedidoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoUserCases {

    @Value("${api.mercadopago.externalPosId}")
    String externalPosId;

    MercadoPagoGateway mercadoPagoGateway;
    PedidoUseCases pedidoUseCases;

    public MercadoPagoServiceImpl(MercadoPagoGateway mercadoPagoGateway, PedidoUseCases pedidoUseCases) {
        this.mercadoPagoGateway = mercadoPagoGateway;
        this.pedidoUseCases = pedidoUseCases;
    }

    @Override
    public OrderMercadoPagoDTO geraOrderMercadoPago(PedidoDTO pedidoDTO, BigDecimal valorTotal) {

        ConfigMercadoPagoOrderDTO config = configuraTipoOrderMercadoPago(externalPosId);
        TransactionsMercadoPagoOrderDTO transactions = geraInformacoesSobreTransacaoMercadoPago(valorTotal);
        List<ItemsMercadoPagoOrderDTO> items = geraItensMercadoPago(pedidoDTO.itens());

        return new OrderMercadoPagoDTO(pedidoDTO,  config,  transactions,  items);
    }

    @Override
    public MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO orderMercadoPagoDTO, PedidoDTO pedidoDTO) {
        return mercadoPagoGateway.geraQrCodePagamento(orderMercadoPagoDTO, pedidoDTO);
    }

    private ConfigMercadoPagoOrderDTO configuraTipoOrderMercadoPago(String externalPosId){
        QrMercadoPagoOrderDTO qrMercadoPagoOrderDTO = new QrMercadoPagoOrderDTO(externalPosId);

        return new ConfigMercadoPagoOrderDTO(qrMercadoPagoOrderDTO);
    }

    private TransactionsMercadoPagoOrderDTO geraInformacoesSobreTransacaoMercadoPago(BigDecimal valorTotal ){
        return new TransactionsMercadoPagoOrderDTO(List.of(new PaymentsMercadoPagoOrderDTO(valorTotal.toString())));
    }

    public List<ItemsMercadoPagoOrderDTO> geraItensMercadoPago(List<ItemPedidoDTO> itemPedidoList){
        List<ItemsMercadoPagoOrderDTO> itemsMercadoPagoOrderDTOList = new ArrayList<>();

        itemPedidoList.forEach(itemPedido -> itemsMercadoPagoOrderDTOList.add(new ItemsMercadoPagoOrderDTO(itemPedido.produto(), itemPedido.quantidade(), itemPedido.preco())));

        return itemsMercadoPagoOrderDTOList;
    }


}
