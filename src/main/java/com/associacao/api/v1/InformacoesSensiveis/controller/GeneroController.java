package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.GeneroService;
import com.associacao.api.v1.InformacoesSensiveis.dto.GeneroResponseDTO;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Genero;
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

@SQLDelete(sql = "UPDATE genero SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/genero")
@Tag(name = "Gênero",
        description = "Operações relacionadas ao gerenciamento e consulta dos gêneros dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class GeneroController extends AbstractController<Genero, GeneroResponseDTO> {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @Override
    protected List<Genero> getAllEntities() {
        return generoService.getAll();
    }

    @Override
    protected Genero saveEntity(Genero entity) {
        return generoService.register(entity);
    }

    @Override
    protected Genero updateEntity(String id, Genero entity) throws Exception {
        return generoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        generoService.delete(id);
    }

    @Override
    protected Genero findEntityById(String id) {
        return generoService.validarId(id);
    }

    @Override
    protected Genero toEntity(GeneroResponseDTO dto) {
        return new Genero(dto.getId(),dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected GeneroResponseDTO toResponseDTO(Genero entity, UserDetails userDetails) {
        GeneroResponseDTO dto = new GeneroResponseDTO(entity.getId(),entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}
