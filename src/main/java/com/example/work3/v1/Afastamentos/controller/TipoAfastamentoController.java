package com.example.work3.v1.Afastamentos.controller;

import com.example.work3.v1.Afastamentos.domain.Listas.TipoAfastamento;
import com.example.work3.v1.Afastamentos.dto.TipoAfastamentoResponseDTO;
import com.example.work3.v1.Afastamentos.service.TipoAfastamentoService;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE tipoafastamento SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/tipoafastamento")
@Tag(name = "Tipo de Afastamento",
        description = "Operações relacionadas ao gerenciamento e consulta dos Tipo de Afastamento oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class TipoAfastamentoController extends AbstractController<TipoAfastamento, TipoAfastamentoResponseDTO> {

    private final TipoAfastamentoService tipoAfastamentoService;

    @Autowired
    public TipoAfastamentoController(TipoAfastamentoService tipoAfastamentoService) {
        this.tipoAfastamentoService = tipoAfastamentoService;
    }

    @Override
    protected List<TipoAfastamento> getAllEntities() {
        return tipoAfastamentoService.getAll();
    }

    @Override
    protected TipoAfastamento saveEntity(TipoAfastamento entity) {
        return tipoAfastamentoService.register(entity);
    }

    @Override
    protected TipoAfastamento updateEntity(String id, TipoAfastamento entity) throws Exception {
        return tipoAfastamentoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        tipoAfastamentoService.delete(id);
    }

    @Override
    protected TipoAfastamento findEntityById(String id) {
        return tipoAfastamentoService.validarId(id);
    }

    @Override
    protected TipoAfastamento toEntity(TipoAfastamentoResponseDTO dto) {
        return new TipoAfastamento(
                dto.getId(),
                dto.getName(),
                dto.getDescricao(),
                dto.isAtivo());
    }

    @Override
    protected TipoAfastamentoResponseDTO toResponseDTO(TipoAfastamento entity, UserDetails userDetails) {
        TipoAfastamentoResponseDTO dto = TipoAfastamentoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }

}
