package com.associacao.api.v1.Treinamentos.mapper;

import com.associacao.api.v1.Treinamentos.domain.Treinamentos;
import com.associacao.api.v1.Treinamentos.dto.TreinamentosResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TreinamentosMapper {

    TreinamentosResponseDTO toResponseDTO(Treinamentos entity);

    Treinamentos toEntity(TreinamentosResponseDTO dto);
}