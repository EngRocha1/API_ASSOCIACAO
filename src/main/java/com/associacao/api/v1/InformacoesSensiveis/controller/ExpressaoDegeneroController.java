package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.ExpressaoDegeneroService;
import com.associacao.api.v1.InformacoesSensiveis.dto.ExpressaoDegeneroResponseDTO;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.ExpressaoDegenero;
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

@SQLDelete(sql = "UPDATE expressaodegenero SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/expressaodegenero")
@Tag(name = "Expressão de Gênero",
        description = "Operações relacionadas ao gerenciamento e consulta das expressões de gênero dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class ExpressaoDegeneroController extends AbstractController<ExpressaoDegenero, ExpressaoDegeneroResponseDTO> {

    private final ExpressaoDegeneroService expressaoDegeneroService;

    @Autowired
    public ExpressaoDegeneroController(ExpressaoDegeneroService expressaoDegeneroService) {
        this.expressaoDegeneroService = expressaoDegeneroService;
    }

    @Override
    protected List<ExpressaoDegenero> getAllEntities() {
        return expressaoDegeneroService.getAll();
    }

    @Override
    protected ExpressaoDegenero saveEntity(ExpressaoDegenero entity) {
        return expressaoDegeneroService.register(entity);
    }

    @Override
    protected ExpressaoDegenero updateEntity(String id, ExpressaoDegenero entity) throws Exception {
        return expressaoDegeneroService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        expressaoDegeneroService.delete(id);
    }

    @Override
    protected ExpressaoDegenero findEntityById(String id) {
        return expressaoDegeneroService.validarId(id);
    }

    @Override
    protected ExpressaoDegenero toEntity(ExpressaoDegeneroResponseDTO dto) {
        return new ExpressaoDegenero(dto.getId(),dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected ExpressaoDegeneroResponseDTO toResponseDTO(ExpressaoDegenero entity, UserDetails userDetails) {
        ExpressaoDegeneroResponseDTO dto = new ExpressaoDegeneroResponseDTO(entity.getId(),entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}
