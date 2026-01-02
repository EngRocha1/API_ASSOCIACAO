package com.associacao.api.v1.Treinamentos.mapper;

import com.associacao.api.v1.Treinamentos.domain.Listas.TreinamentoCurso;
import com.associacao.api.v1.Treinamentos.dto.TreinamentoCursoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TreinamentoCursoMapper {

    TreinamentoCursoResponseDTO toResponseDTO(TreinamentoCurso entity);

    TreinamentoCurso toEntity(TreinamentoCursoResponseDTO dto);
}