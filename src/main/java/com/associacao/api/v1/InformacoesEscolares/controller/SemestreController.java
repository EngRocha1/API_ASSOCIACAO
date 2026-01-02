package com.associacao.api.v1.InformacoesEscolares.controller;

import com.associacao.api.v1.InformacoesEscolares.Service.SemestreService;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Semestre;
import com.associacao.api.v1.InformacoesEscolares.dto.SemestreResponseDTO;
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

@SQLDelete(sql = "UPDATE semestrecurso SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/semestrecurso")
@Tag(name = "Semestre do Curso",
        description = "Operações relacionadas ao gerenciamento e consulta dos semestres dos cursos, incluindo criação, atualização, listagem e exclusão.")

public class SemestreController extends AbstractController<Semestre, SemestreResponseDTO> {

    private final SemestreService semestreService;

    @Autowired
    public SemestreController(SemestreService semestreService) {
        this.semestreService = semestreService;
    }

    @Override
    protected List<Semestre> getAllEntities() {
        return semestreService.getAll();
    }

    @Override
    protected Semestre saveEntity(Semestre entity) {
        return semestreService.register(entity);
    }

    @Override
    protected Semestre updateEntity(String id, Semestre entity) throws Exception {
        return semestreService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        semestreService.delete(id);
    }

    @Override
    protected Semestre findEntityById(String id) {
        return semestreService.validarId(id);
    }

    @Override
    protected Semestre toEntity(SemestreResponseDTO dto) {
        return new Semestre(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected SemestreResponseDTO toResponseDTO(Semestre entity, UserDetails userDetails) {
        SemestreResponseDTO dto = SemestreResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
