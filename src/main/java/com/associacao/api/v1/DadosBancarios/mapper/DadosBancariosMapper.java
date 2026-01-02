package com.associacao.api.v1.DadosBancarios.mapper;

import com.associacao.api.v1.DadosBancarios.domain.DadosBancarios;
import com.associacao.api.v1.DadosBancarios.dto.DadosBancariosResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DadosBancariosMapper {
    DadosBancariosMapper INSTANCE = Mappers.getMapper(DadosBancariosMapper.class);
    DadosBancariosResponseDTO toResponseDTO(DadosBancarios entity);
}