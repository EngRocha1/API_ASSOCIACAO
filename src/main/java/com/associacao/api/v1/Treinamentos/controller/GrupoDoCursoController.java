package com.associacao.api.v1.Treinamentos.controller;

import com.associacao.api.v1.Treinamentos.domain.Listas.GrupoDoCurso;
import com.associacao.api.v1.Treinamentos.dto.GrupoDoCursoResponseDTO;
import com.associacao.api.v1.Treinamentos.service.GrupoDoCursoService;
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
@SQLDelete(sql = "UPDATE grupo_do_curso SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/grupo-do-curso")
@Tag(name = "GrupoDoCurso",
        description = "Operações relacionadas ao gerenciamento e consulta dos Grupos de Curso, incluindo criação, atualização, listagem e exclusão.")
public class GrupoDoCursoController extends AbstractController<GrupoDoCurso, GrupoDoCursoResponseDTO> {

    private final GrupoDoCursoService grupoDoCursoService;

    @Autowired
    public GrupoDoCursoController(GrupoDoCursoService grupoDoCursoService) {
        this.grupoDoCursoService = grupoDoCursoService;
    }

    @Override
    protected List<GrupoDoCurso> getAllEntities() {
        return grupoDoCursoService.getAll();
    }

    @Override
    protected GrupoDoCurso saveEntity(GrupoDoCurso entity) {
        return grupoDoCursoService.register(entity);
    }

    @Override
    protected GrupoDoCurso updateEntity(String id, GrupoDoCurso entity) throws Exception {
        return grupoDoCursoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        grupoDoCursoService.delete(id);
    }

    @Override
    protected GrupoDoCurso findEntityById(String id) {
        return grupoDoCursoService.validarId(id);
    }

    @Override
    protected GrupoDoCurso toEntity(GrupoDoCursoResponseDTO dto) {
        return new GrupoDoCurso(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected GrupoDoCursoResponseDTO toResponseDTO(GrupoDoCurso entity, UserDetails userDetails) {
        GrupoDoCursoResponseDTO dto = GrupoDoCursoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}