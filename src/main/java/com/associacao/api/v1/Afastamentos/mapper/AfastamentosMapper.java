package com.associacao.api.v1.Afastamentos.mapper;
import com.associacao.api.v1.Afastamentos.domain.Afastamentos;
import com.associacao.api.v1.Afastamentos.dto.AfastamentosResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AfastamentosMapper {
    AfastamentosMapper INSTANCE = Mappers.getMapper(AfastamentosMapper.class);
    AfastamentosResponseDTO toResponseDTO(Afastamentos entity);
}