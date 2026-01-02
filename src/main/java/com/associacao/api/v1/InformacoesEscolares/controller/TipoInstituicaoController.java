package com.associacao.api.v1.InformacoesEscolares.controller;

import com.associacao.api.v1.InformacoesEscolares.Service.TipoInstituicaoService;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.TipoInstituicao;
import com.associacao.api.v1.InformacoesEscolares.dto.TipoInstituicaoDTO;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
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

@SQLDelete(sql = "UPDATE tipoinstituicao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/tipoinstituicao")
@Tag(name = "Tipo de Instituições",
        description = "Operações relacionadas ao gerenciamento e consulta dos tipos de instituições de ensino, incluindo criação, atualização, listagem e exclusão.")

public class TipoInstituicaoController extends AbstractController<TipoInstituicao, TipoInstituicaoDTO> {

    private final TipoInstituicaoService tipoInstituicaoService;

    @Autowired
    public TipoInstituicaoController(TipoInstituicaoService tipoInstituicaoService) {
        this.tipoInstituicaoService = tipoInstituicaoService;
    }

    @Override
    protected List<TipoInstituicao> getAllEntities() {
        return tipoInstituicaoService.getAll();
    }

    @Override
    protected TipoInstituicao saveEntity(TipoInstituicao entity) {
        return tipoInstituicaoService.register(entity);
    }

    @Override
    protected TipoInstituicao updateEntity(String id, TipoInstituicao entity) throws Exception {
        return tipoInstituicaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        tipoInstituicaoService.delete(id);
    }

    @Override
    protected TipoInstituicao findEntityById(String id) {
        return tipoInstituicaoService.validarId(id);
    }

    @Override
    protected TipoInstituicao toEntity(TipoInstituicaoDTO dto) {
        return new TipoInstituicao(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected TipoInstituicaoDTO toResponseDTO(TipoInstituicao entity, UserDetails userDetails) {
        TipoInstituicaoDTO dto = TipoInstituicaoDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
