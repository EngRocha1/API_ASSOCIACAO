package com.associacao.api.v1.Treinamentos.controller;

import com.associacao.api.v1.Treinamentos.domain.Listas.TreinamentoCurso;
import com.associacao.api.v1.Treinamentos.dto.TreinamentoCursoResponseDTO;
import com.associacao.api.v1.Treinamentos.mapper.TreinamentoCursoMapper;
import com.associacao.api.v1.Treinamentos.service.TreinamentoCursoService;
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
@SQLDelete(sql = "UPDATE treinamento_curso SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/treinamento-cursos")
@Tag(name = "Treinamento Cursos",
        description = "Operações relacionadas ao gerenciamento e consulta dos Treinamento Cursos, incluindo criação, atualização, listagem e exclusão.")
public class TreinamentoCursoController extends AbstractController<TreinamentoCurso, TreinamentoCursoResponseDTO> {

    private final TreinamentoCursoService treinamentoCursoService;
    private final TreinamentoCursoMapper treinamentoCursoMapper;

    @Autowired
    public TreinamentoCursoController(
            TreinamentoCursoService treinamentoCursoService,
            TreinamentoCursoMapper treinamentoCursoMapper
    ) {
        this.treinamentoCursoService = treinamentoCursoService;
        this.treinamentoCursoMapper = treinamentoCursoMapper;
    }

    @Override
    protected List<TreinamentoCurso> getAllEntities() {
        return treinamentoCursoService.getAll();
    }

    @Override
    protected TreinamentoCurso saveEntity(TreinamentoCurso entity) {
        return treinamentoCursoService.register(entity);
    }

    @Override
    protected TreinamentoCurso updateEntity(String id, TreinamentoCurso entity) throws Exception {
        return treinamentoCursoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        treinamentoCursoService.delete(id);
    }

    @Override
    protected TreinamentoCurso findEntityById(String id) {
        return treinamentoCursoService.findById(id);
    }

    @Override
    protected TreinamentoCurso toEntity(TreinamentoCursoResponseDTO dto) {
        return treinamentoCursoMapper.toEntity(dto);
    }

    @Override
    protected TreinamentoCursoResponseDTO toResponseDTO(TreinamentoCurso entity, UserDetails userDetails) {
        TreinamentoCursoResponseDTO dto = treinamentoCursoMapper.toResponseDTO(entity);
        return populateAdminFields(dto, entity, userDetails);
    }
}