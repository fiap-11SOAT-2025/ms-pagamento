package org.fiap.Pagamento.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fiap.Pagamento.core.entities.Pagamento;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PAGAMENTO")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @OneToOne
    @JoinColumn(name = "pedido_id", unique = true)
    private PedidoEntity pedido;

    private String qrCodeMercadoPago;

    private String externalReferenceMercadoPago;

    private Integer statusPagamentoId;

    public PagamentoEntity(PedidoEntity pedido, String qrCodeMercadoPago, Integer statusPagamentoId) {
        this.pedido = pedido;
        this.qrCodeMercadoPago = qrCodeMercadoPago;
        this.statusPagamentoId = statusPagamentoId;
    }

    public PagamentoEntity(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.pedido = new PedidoEntity(pagamento.getPedido());
        this.qrCodeMercadoPago = pagamento.getQrCodeMercadoPago();
        this.externalReferenceMercadoPago = pagamento.getExternalReferenceMercadoPago();

        this.statusPagamentoId = pagamento.getStatusPagamentoId();
    }
}
