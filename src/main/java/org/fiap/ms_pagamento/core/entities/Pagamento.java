package org.fiap.ms_pagamento.core.entities;


public class Pagamento {

    private Long id ;
    private String pedidoId;
    private String qrCodeMercadoPago;
    private String externalReferenceMercadoPago;
    private Integer statusPagamentoId;

    public Pagamento(Long id, String pedidoId, String qrCodeMercadoPago, String externalReferenceMercadoPago,
                     Integer statusPagamentoId) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("Pagamento deve estar associado a um pedido.");
        }
        if (statusPagamentoId == null) {
            throw new IllegalArgumentException("Status do pagamento é obrigatório.");
        }
        this.id = id;
        this.pedidoId = pedidoId;
        this.qrCodeMercadoPago = qrCodeMercadoPago;
        this.externalReferenceMercadoPago = externalReferenceMercadoPago;
        this.statusPagamentoId = statusPagamentoId;
    }

    public Long getId() {
        return id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public String getQrCodeMercadoPago() {
        return qrCodeMercadoPago;
    }

    public void setQrCodeMercadoPago(String qrCodeMercadoPago) {
        this.qrCodeMercadoPago = qrCodeMercadoPago;
    }

    public Integer getStatusPagamentoId() {
        return statusPagamentoId;
    }

    public String getExternalReferenceMercadoPago() {
        return externalReferenceMercadoPago;
    }

    public void setExternalReferenceMercadoPago(String externalReferenceMercadoPago) {
        this.externalReferenceMercadoPago = externalReferenceMercadoPago;
    }

    public void setStatusPagamentoId(Integer statusPagamentoId) {
        this.statusPagamentoId = statusPagamentoId;
    }

    public Pagamento(String pedidoId, String qrCodeMercadoPago, String externalReferenceMercadoPago, Integer statusPagamentoId) {
        this.pedidoId = pedidoId;
        this.qrCodeMercadoPago = qrCodeMercadoPago;
        this.externalReferenceMercadoPago = externalReferenceMercadoPago;
        this.statusPagamentoId = statusPagamentoId;
    }
}
