package org.fiap.Pagamento.core.entities;


public class Pagamento {

    private Long id ;
    private Pedido pedido;
    private String qrCodeMercadoPago;
    private String externalReferenceMercadoPago;
    private Integer statusPagamentoId;

    public Pagamento(Long id, Pedido pedido, String qrCodeMercadoPago, String externalReferenceMercadoPago,
                     Integer statusPagamentoId) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pagamento deve estar associado a um pedido.");
        }
        if (statusPagamentoId == null) {
            throw new IllegalArgumentException("Status do pagamento é obrigatório.");
        }
        this.id = id;
        this.pedido = pedido;
        this.qrCodeMercadoPago = qrCodeMercadoPago;
        this.externalReferenceMercadoPago = externalReferenceMercadoPago;
        this.statusPagamentoId = statusPagamentoId;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
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

    public Pagamento(Pedido pedido, String qrCodeMercadoPago, String externalReferenceMercadoPago, Integer statusPagamentoId) {
        this.pedido = pedido;
        this.qrCodeMercadoPago = qrCodeMercadoPago;
        this.externalReferenceMercadoPago = externalReferenceMercadoPago;
        this.statusPagamentoId = statusPagamentoId;
    }
}
