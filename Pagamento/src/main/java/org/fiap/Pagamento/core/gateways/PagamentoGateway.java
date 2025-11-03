package org.fiap.Pagamento.core.gateways;

import org.fiap.Pagamento.core.entities.Pagamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PagamentoGateway {

    Optional<Pagamento> save(Pagamento pagamento);

    Optional<Pagamento> findByPedidoId (String pedidoId);

    Optional<Pagamento> findByexternalReferenceMercadoPago (String externalReferenceMercadoPago);

    List<Pagamento> findAll();
}
