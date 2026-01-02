package com.associacao.api.v1.InformacoesEscolares.controller;

import com.associacao.api.v1.InformacoesEscolares.Service.NivelEnsinoService;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.NivelEnsino;
import com.associacao.api.v1.InformacoesEscolares.dto.NivelEnsinoDTO;
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

@SQLDelete(sql = "UPDATE nivelensino SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/nivelensino")
@Tag(name = "Nível de Ensino",
        description = "Operações relacionadas ao gerenciamento e consulta dos níveis de ensino, incluindo criação, atualização, listagem e exclusão.")

public class NivelEnsinoController extends AbstractController<NivelEnsino, NivelEnsinoDTO> {

    private final NivelEnsinoService nivelEnsinoService;

    @Autowired
    public NivelEnsinoController(NivelEnsinoService nivelEnsinoService) {
        this.nivelEnsinoService = nivelEnsinoService;
    }

    @Override
    protected List<NivelEnsino> getAllEntities() {
        return nivelEnsinoService.getAll();
    }

    @Override
    protected NivelEnsino saveEntity(NivelEnsino entity) {
        return nivelEnsinoService.register(entity);
    }

    @Override
    protected NivelEnsino updateEntity(String id, NivelEnsino entity) throws Exception {
        return nivelEnsinoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        nivelEnsinoService.delete(id);
    }

    @Override
    protected NivelEnsino findEntityById(String id) {
        return nivelEnsinoService.validarId(id);
    }

    @Override
    protected NivelEnsino toEntity(NivelEnsinoDTO dto) {
        return new NivelEnsino(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected NivelEnsinoDTO toResponseDTO(NivelEnsino entity, UserDetails userDetails) {
        NivelEnsinoDTO dto = NivelEnsinoDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
