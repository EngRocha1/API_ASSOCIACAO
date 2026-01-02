package com.associacao.api.v1.InformacoesEscolares.controller;

import com.associacao.api.v1.InformacoesEscolares.Service.CursoService;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Curso;
import com.associacao.api.v1.InformacoesEscolares.dto.CursoResponseDTO;
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
@SQLDelete(sql = "UPDATE curso SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/curso")
@Tag(name = "Cursos",
        description = "Operações relacionadas ao gerenciamento e consulta dos cursos oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class CursoController extends AbstractController<Curso, CursoResponseDTO> {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Override
    protected List<Curso> getAllEntities() {
        return cursoService.getAll();
    }

    @Override
    protected Curso saveEntity(Curso entity) {
        return cursoService.register(entity);
    }

    @Override
    protected Curso updateEntity(String id, Curso entity) throws Exception {
        return cursoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        cursoService.delete(id);
    }

    @Override
    protected Curso findEntityById(String id) {
        return cursoService.validarId(id);
    }

    @Override
    protected Curso toEntity(CursoResponseDTO dto) {
        return new Curso(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected CursoResponseDTO toResponseDTO(Curso entity, UserDetails userDetails) {
        CursoResponseDTO dto = CursoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
