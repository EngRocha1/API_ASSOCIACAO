package com.example.work3.v1.Afastamentos.mapper;
import com.example.work3.v1.Afastamentos.domain.Afastamentos;
import com.example.work3.v1.Afastamentos.dto.AfastamentosResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AfastamentosMapper {
    AfastamentosMapper INSTANCE = Mappers.getMapper(AfastamentosMapper.class);
    AfastamentosResponseDTO toResponseDTO(Afastamentos entity);
}