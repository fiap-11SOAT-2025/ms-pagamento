package org.fiap.ms_pagamento.infrastructure.mappers;


import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.core.enums.StatusPagamentoEnum;
import org.fiap.ms_pagamento.infrastructure.persistence.PagamentoEntity;
import org.fiap.ms_pagamento.presentation.dto.pagamento.PagamentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagamentoResponseMapper {

    @Mapping(source = "statusPagamentoId", target = "statusPagamento", qualifiedByName = "statusPagamentoIdToStatusPagamento")
    PagamentoDTO entidadeParaDto(Pagamento pedido);

    List<PagamentoDTO> entidadesParaDtos(List<Pagamento> pagamentos);

    // Método toDomain com @Context para injetar dependências de mappers
    default Pagamento toDomain(PagamentoEntity entity) {

        if (entity == null) return null;

        return new Pagamento(
                entity.getId(),
                entity.getPedidoId(),
                entity.getQrCodeMercadoPago(),
                entity.getExternalReferenceMercadoPago(),
                entity.getStatusPagamentoId()
        );
    }

    @Named("statusPagamentoIdToStatusPagamento")
    default String mapStatusPagamento(Integer statusPagamentoId) {
        if (statusPagamentoId == null) return null;
        return StatusPagamentoEnum.fromCodigo(statusPagamentoId).name();
    }
}
