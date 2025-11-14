package org.fiap.ms_pagamento.infrastructure.gateways;

import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.gateways.PagamentoGateway;

import org.fiap.ms_pagamento.infrastructure.mappers.PagamentoResponseMapper;
import org.fiap.ms_pagamento.infrastructure.persistence.PagamentoEntity;
import org.fiap.ms_pagamento.infrastructure.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PagamentoResponseMapper mapper;

    @Override
    public Optional<Pagamento> save(Pagamento pagamento) {
        PagamentoEntity entity = new PagamentoEntity(pagamento);
        PagamentoEntity savedEntity = repository.save(entity);

        return Optional.of(mapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Pagamento> findByPedidoId(String pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .map(entity -> mapper.toDomain(entity));

    }

    @Override
    public Optional<Pagamento> findByexternalReferenceMercadoPago(String externalReferenceMercadoPago) {
        return repository.findByExternalReferenceMercadoPago(externalReferenceMercadoPago)
                .map(entity -> mapper.toDomain(entity));
    }

    @Override
    public List<Pagamento> findAll() {
        return repository.findAll().stream()
                .map(entity -> mapper.toDomain(entity))
                .toList();
    }
}
