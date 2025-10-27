package org.fiap.Pagamento.infrastructure.mappers;


import org.fiap.Pagamento.core.entities.Pagamento;
import org.fiap.Pagamento.core.enums.StatusPagamentoEnum;
import org.fiap.Pagamento.infrastructure.persistence.PagamentoEntity;
import org.fiap.Pagamento.presentation.dto.pagamento.PagamentoDTO;
import org.mapstruct.Context;
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
                entity.pedidoId(),
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
