package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Lotacao;
import com.associacao.api.v1.InformacoesAssentamento.dto.LotacaoResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.LotacaoService;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
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

@SQLDelete(sql = "UPDATE lotacao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/lotacao")
@Tag(name = "Lotacao",
        description = "Operações relacionadas ao gerenciamento e consulta dos Lotacao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class LotacaoController extends AbstractController<Lotacao, LotacaoResponseDTO> {

    private final LotacaoService lotacaoService;

    @Autowired
    public LotacaoController(LotacaoService lotacaoService) {
        this.lotacaoService = lotacaoService;
    }

    @Override
    protected List<Lotacao> getAllEntities() {
        return lotacaoService.getAll();
    }

    @Override
    protected Lotacao saveEntity(Lotacao entity) {
        return lotacaoService.register(entity);
    }

    @Override
    protected Lotacao updateEntity(String id, Lotacao entity) throws Exception {
        return lotacaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        lotacaoService.delete(id);
    }

    @Override
    protected Lotacao findEntityById(String id) {
        return lotacaoService.validarId(id);
    }

    @Override
    protected Lotacao toEntity(LotacaoResponseDTO dto) {
        return new Lotacao(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected LotacaoResponseDTO toResponseDTO(Lotacao entity, UserDetails userDetails) {
        LotacaoResponseDTO dto = LotacaoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
