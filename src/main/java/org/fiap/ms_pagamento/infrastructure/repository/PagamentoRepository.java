package org.fiap.ms_pagamento.infrastructure.repository;


import org.fiap.ms_pagamento.infrastructure.persistence.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

    Optional<PagamentoEntity> findByPedidoId(String pedidoId);

    Optional<PagamentoEntity> findByExternalReferenceMercadoPago(String externalReferenceMercadoPago);
}
