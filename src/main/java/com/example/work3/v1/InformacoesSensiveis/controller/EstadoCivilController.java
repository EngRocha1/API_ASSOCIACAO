package com.example.work3.v1.InformacoesSensiveis.controller;
import com.example.work3.v1.InformacoesSensiveis.Service.EstadoCivilService;
import com.example.work3.v1.InformacoesSensiveis.dto.EstadoCivilResponseDTO;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.EstadoCivil;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
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
@SQLDelete(sql = "UPDATE estadocivil SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/estadocivil")
@Tag(name = "Estado Civil",
        description = "Operações relacionadas ao gerenciamento e consulta de estados civis dos usuários, incluindo criação, atualização, listagem e exclusão.")
public class EstadoCivilController extends AbstractController<EstadoCivil, EstadoCivilResponseDTO> {

    private final EstadoCivilService estadoCivilService;

    @Autowired
    public EstadoCivilController(EstadoCivilService estadoCivilService) {
        this.estadoCivilService = estadoCivilService;
    }

    @Override
    protected List<EstadoCivil> getAllEntities() {
        return estadoCivilService.getAll();
    }

    @Override
    protected EstadoCivil saveEntity(EstadoCivil entity) {
        return estadoCivilService.register(entity);
    }

    @Override
    protected EstadoCivil updateEntity(String id, EstadoCivil entity) throws Exception {
        return estadoCivilService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        estadoCivilService.delete(id);
    }

    @Override
    protected EstadoCivil findEntityById(String id) {
        return estadoCivilService.validarId(id);
    }

    @Override
    protected EstadoCivil toEntity(EstadoCivilResponseDTO dto) {
        return new EstadoCivil(
                dto.getId(),
                dto.getName(),
                dto.getDescricao(),
                dto.isAtivo());
    }

    @Override
    protected EstadoCivilResponseDTO toResponseDTO(EstadoCivil entity, UserDetails userDetails) {
        EstadoCivilResponseDTO dto =
                new EstadoCivilResponseDTO(
                        entity.getId(),
                        entity.getName(),
                        entity.getDescricao(),
                        entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}
