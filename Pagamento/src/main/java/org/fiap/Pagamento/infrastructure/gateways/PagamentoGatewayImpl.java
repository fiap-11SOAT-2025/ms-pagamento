package org.fiap.Pagamento.infrastructure.gateways;

]
import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.core.gateways.PagamentoGateway;
import org.fiap.Pagamento.infrastructure.mappers.PagamentoResponseMapper;
import org.fiap.Pagamento.infrastructure.persistence.PagamentoEntity;
import org.fiap.Pagamento.infrastructure.repository.PagamentoRepository;
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
    public Optional<Pagamento> findByPedidoId(Long pedidoId) {
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
