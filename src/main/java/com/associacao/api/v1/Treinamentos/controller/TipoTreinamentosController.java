package com.associacao.api.v1.Treinamentos.controller;

import com.associacao.api.v1.Treinamentos.domain.Listas.TipoTreinamentos;
import com.associacao.api.v1.Treinamentos.dto.TipoTreinamentosResponseDTO;
import com.associacao.api.v1.Treinamentos.service.TipoTreinamentosService;
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
@SQLDelete(sql = "UPDATE tipotreinamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/tipotreinamentos")
@Tag(name = "Tipo Treinamentos",
        description = "Operações relacionadas ao gerenciamento e consulta dos TipoTreinamentos oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class TipoTreinamentosController extends AbstractController<TipoTreinamentos, TipoTreinamentosResponseDTO> {

    private final TipoTreinamentosService TipoTreinamentosService;

    @Autowired
    public TipoTreinamentosController(TipoTreinamentosService TipoTreinamentosService) {
        this.TipoTreinamentosService = TipoTreinamentosService;
    }

    @Override
    protected List<TipoTreinamentos> getAllEntities() {
        return TipoTreinamentosService.getAll();
    }

    @Override
    protected TipoTreinamentos saveEntity(TipoTreinamentos entity) {
        return TipoTreinamentosService.register(entity);
    }

    @Override
    protected TipoTreinamentos updateEntity(String id, TipoTreinamentos entity) throws Exception {
        return TipoTreinamentosService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        TipoTreinamentosService.delete(id);
    }

    @Override
    protected TipoTreinamentos findEntityById(String id) {
        return TipoTreinamentosService.validarId(id);
    }

    @Override
    protected TipoTreinamentos toEntity(TipoTreinamentosResponseDTO dto) {
        return new TipoTreinamentos(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected TipoTreinamentosResponseDTO toResponseDTO(TipoTreinamentos entity, UserDetails userDetails) {
        TipoTreinamentosResponseDTO dto = TipoTreinamentosResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
