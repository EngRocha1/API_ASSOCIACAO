package com.associacao.api.v1.DocumentosPessoais.mapper;

import com.associacao.api.v1.DocumentosPessoais.domain.DocumentosPessoais;
import com.associacao.api.v1.DocumentosPessoais.dto.DocumentosPessoaisResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentosPessoaisMapper {

    DocumentosPessoaisMapper INSTANCE = Mappers.getMapper(DocumentosPessoaisMapper.class);
    DocumentosPessoaisResponseDTO toResponseDTO(DocumentosPessoais entity);
    DocumentosPessoais toEntity(DocumentosPessoaisResponseDTO dto);
}