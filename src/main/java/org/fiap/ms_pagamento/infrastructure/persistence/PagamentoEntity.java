package org.fiap.ms_pagamento.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fiap.ms_pagamento.core.entities.Pagamento;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PAGAMENTO")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String pedidoId;

    private String qrCodeMercadoPago;

    private String externalReferenceMercadoPago;

    private Integer statusPagamentoId;


    public PagamentoEntity(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.pedidoId = pagamento.getPedidoId();
        this.qrCodeMercadoPago = pagamento.getQrCodeMercadoPago();
        this.externalReferenceMercadoPago = pagamento.getExternalReferenceMercadoPago();

        this.statusPagamentoId = pagamento.getStatusPagamentoId();
    }
}
