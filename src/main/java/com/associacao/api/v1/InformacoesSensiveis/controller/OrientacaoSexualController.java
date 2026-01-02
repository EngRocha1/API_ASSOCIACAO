package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.OrientacaoSexualService;
import com.associacao.api.v1.InformacoesSensiveis.dto.OrientacaoSexualResponseDTO;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.OrientacaoSexual;
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

@SQLDelete(sql = "UPDATE orientacaosexual SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/orientacaosexual")
@Tag(name = "Orientação Sexual",
        description = "Operações relacionadas ao gerenciamento e consulta das orientações sexuais dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class OrientacaoSexualController extends AbstractController<OrientacaoSexual, OrientacaoSexualResponseDTO> {

    private final OrientacaoSexualService orientacaoSexualService;

    @Autowired
    public OrientacaoSexualController(OrientacaoSexualService orientacaoSexualService) {
        this.orientacaoSexualService = orientacaoSexualService;
    }

    @Override
    protected List<OrientacaoSexual> getAllEntities() {
        return orientacaoSexualService.getAll();
    }

    @Override
    protected OrientacaoSexual saveEntity(OrientacaoSexual entity) {
        return orientacaoSexualService.register(entity);
    }

    @Override
    protected OrientacaoSexual updateEntity(String id, OrientacaoSexual entity) throws Exception {
        return orientacaoSexualService.update(id, entity);
    }


    @Override
    @Transactional
    protected void deleteEntity(String id) {
        orientacaoSexualService.delete(id);
    }

    @Override
    protected OrientacaoSexual findEntityById(String id) {
        return orientacaoSexualService.validarId(id);
    }

    @Override
    protected OrientacaoSexual toEntity(OrientacaoSexualResponseDTO dto) {
        return new OrientacaoSexual(dto.getId(),dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected OrientacaoSexualResponseDTO toResponseDTO(OrientacaoSexual entity, UserDetails userDetails) {
        OrientacaoSexualResponseDTO dto = new OrientacaoSexualResponseDTO(entity.getId(),entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}
