package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Superintendencia;
import com.associacao.api.v1.InformacoesAssentamento.dto.SuperintendenciaResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.SuperintendenciaService;
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

@SQLDelete(sql = "UPDATE superintendencia SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/superintendencia")
@Tag(name = "Superintendencia",
        description = "Operações relacionadas ao gerenciamento e consulta dos Lotacao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class SuperintendenciaController extends AbstractController<Superintendencia, SuperintendenciaResponseDTO> {

    private final SuperintendenciaService superintendenciaService;

    @Autowired
    public SuperintendenciaController(SuperintendenciaService superintendenciaService) {
        this.superintendenciaService = superintendenciaService;
    }

    @Override
    protected List<Superintendencia> getAllEntities() {
        return superintendenciaService.getAll();
    }

    @Override
    protected Superintendencia saveEntity(Superintendencia entity) {
        return superintendenciaService.register(entity);
    }

    @Override
    protected Superintendencia updateEntity(String id, Superintendencia entity) throws Exception {
        return superintendenciaService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        superintendenciaService.delete(id);
    }

    @Override
    protected Superintendencia findEntityById(String id) {
        return superintendenciaService.validarId(id);
    }

    @Override
    protected Superintendencia toEntity(SuperintendenciaResponseDTO dto) {
        return new Superintendencia(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected SuperintendenciaResponseDTO toResponseDTO(Superintendencia entity, UserDetails userDetails) {
        SuperintendenciaResponseDTO dto = SuperintendenciaResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
