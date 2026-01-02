package com.example.work3.v1.Afastamentos.controller;

import com.example.work3.v1.Afastamentos.domain.Listas.FluxoAprovacao;
import com.example.work3.v1.Afastamentos.dto.FluxoAprovacaoResponseDTO;
import com.example.work3.v1.Afastamentos.service.FluxoAprovacaoService;
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
@SQLDelete(sql = "UPDATE fluxoaprovacao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/fluxoaprovacao")
@Tag(name = "FluxoAprovacao",
        description = "Operações relacionadas ao gerenciamento e consulta dos Tipo de FluxoAprovacao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class FluxoAprovacaoController extends AbstractController<FluxoAprovacao, FluxoAprovacaoResponseDTO> {

    private final FluxoAprovacaoService fluxoAprovacaoService;

    @Autowired
    public FluxoAprovacaoController(FluxoAprovacaoService fluxoAprovacaoService) {
        this.fluxoAprovacaoService = fluxoAprovacaoService;
    }

    @Override
    protected List<FluxoAprovacao> getAllEntities() {
        return fluxoAprovacaoService.getAll();
    }

    @Override
    protected FluxoAprovacao saveEntity(FluxoAprovacao entity) {
        return fluxoAprovacaoService.register(entity);
    }

    @Override
    protected FluxoAprovacao updateEntity(String id, FluxoAprovacao entity) throws Exception {
        return fluxoAprovacaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        fluxoAprovacaoService.delete(id);
    }

    @Override
    protected FluxoAprovacao findEntityById(String id) {
        return fluxoAprovacaoService.validarId(id);
    }

    @Override
    protected FluxoAprovacao toEntity(FluxoAprovacaoResponseDTO dto) {
        return new FluxoAprovacao(
                dto.getId(),
                dto.getName(),
                dto.getDescricao(),
                dto.getAprovacaoTipo(),
                dto.getDataAprovacao(),
                dto.getDataSolicitacao(),
                dto.getAprovadoPor(),
                dto.getSolicitadoPor(),
                dto.isAtivo());
    }

    @Override
    protected FluxoAprovacaoResponseDTO toResponseDTO(FluxoAprovacao entity, UserDetails userDetails) {
        FluxoAprovacaoResponseDTO dto = FluxoAprovacaoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .SolicitadoPor(entity.getSolicitadoPor())
                .DataAprovacao(entity.getDataAprovacao())
                .DataSolicitacao(entity.getDataSolicitacao())
                .AprovadoPor(entity.getAprovadoPor())
                .AprovacaoTipo(entity.getAprovacaoTipo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
