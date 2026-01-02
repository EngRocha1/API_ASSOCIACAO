package com.example.work3.v1.InformacoesEscolares.controller;

import com.example.work3.v1.InformacoesEscolares.Service.StatusFormacaoService;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.StatusFormacao;
import com.example.work3.v1.InformacoesEscolares.dto.StatusFormacaoResponseDTO;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController

@SQLDelete(sql = "UPDATE statusformacao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/statusformacao")
@Tag(name = "Status de Formação",
        description = "Operações relacionadas ao gerenciamento e consulta dos status de formação dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class StatusFormacaoController extends AbstractController<StatusFormacao, StatusFormacaoResponseDTO> {

    private final StatusFormacaoService statusFormacaoService;

    @Autowired
    public StatusFormacaoController(StatusFormacaoService statusFormacaoService) {
        this.statusFormacaoService = statusFormacaoService;
    }

    @Override
    protected List<StatusFormacao> getAllEntities() {
        return statusFormacaoService.getAll();
    }

    @Override
    protected StatusFormacao saveEntity(StatusFormacao entity) {
        return statusFormacaoService.register(entity);
    }

    @Override
    protected StatusFormacao updateEntity(String id, StatusFormacao entity) throws Exception {
        return statusFormacaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        statusFormacaoService.delete(id);
    }

    @Override
    protected StatusFormacao findEntityById(String id) {
        return statusFormacaoService.validarId(id);
    }

    @Override
    protected StatusFormacao toEntity(StatusFormacaoResponseDTO dto) {
        return new StatusFormacao(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected StatusFormacaoResponseDTO toResponseDTO(StatusFormacao entity, UserDetails userDetails) {
        StatusFormacaoResponseDTO dto = StatusFormacaoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
