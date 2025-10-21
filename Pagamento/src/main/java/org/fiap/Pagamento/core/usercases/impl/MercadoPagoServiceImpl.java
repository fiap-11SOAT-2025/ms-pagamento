package org.fiap.Pagamento.core.usercases.impl;


import org.fiap.Pagamento.core.gateways.MercadoPagoGateway;
import org.fiap.Pagamento.core.usercases.MercadoPagoUserCases;
import org.fiap.Pagamento.presentation.dto.mercadopago.*;
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

    public MercadoPagoServiceImpl(MercadoPagoGateway mercadoPagoGateway) {
        this.mercadoPagoGateway = mercadoPagoGateway;
    }

    @Override
    public OrderMercadoPagoDTO geraOrderMercadoPago(Pedido pedido, BigDecimal valorTotal) {

        ConfigMercadoPagoOrderDTO config = configuraTipoOrderMercadoPago(externalPosId);
        TransactionsMercadoPagoOrderDTO transactions = geraInformacoesSobreTransacaoMercadoPago(valorTotal);
        List<ItemsMercadoPagoOrderDTO> items = geraItensMercadoPago(pedido.getItens());

        return new OrderMercadoPagoDTO(pedido,  config,  transactions,  items);
    }

    @Override
    public MercadoPagoOrderResponseDTO geraQrCodePagamento(OrderMercadoPagoDTO orderMercadoPagoDTO, Pedido pedido) {
        return mercadoPagoGateway.geraQrCodePagamento(orderMercadoPagoDTO, pedido);
    }

    private ConfigMercadoPagoOrderDTO configuraTipoOrderMercadoPago(String externalPosId){
        QrMercadoPagoOrderDTO qrMercadoPagoOrderDTO = new QrMercadoPagoOrderDTO(externalPosId);

        return new ConfigMercadoPagoOrderDTO(qrMercadoPagoOrderDTO);
    }

    private TransactionsMercadoPagoOrderDTO geraInformacoesSobreTransacaoMercadoPago(BigDecimal valorTotal ){
        return new TransactionsMercadoPagoOrderDTO(List.of(new PaymentsMercadoPagoOrderDTO(valorTotal.toString())));
    }

    public List<ItemsMercadoPagoOrderDTO> geraItensMercadoPago(List<ItemPedido> itemPedidoList){
        List<ItemsMercadoPagoOrderDTO> itemsMercadoPagoOrderDTOList = new ArrayList<>();

        itemPedidoList.forEach(itemPedido -> itemsMercadoPagoOrderDTOList.add(new ItemsMercadoPagoOrderDTO(itemPedido.getProduto(), itemPedido.getQuantidade())));

        return itemsMercadoPagoOrderDTOList;
    }


}
