package org.fiap.ms_pagamento.core.gateways;

import org.fiap.ms_pagamento.core.entities.Pagamento;
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
