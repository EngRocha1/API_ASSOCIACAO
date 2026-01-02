package com.example.work3.v1.InformacoesAssentamento.controller;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Simbolo;
import com.example.work3.v1.InformacoesAssentamento.dto.SimboloResponseDTO;
import com.example.work3.v1.InformacoesAssentamento.service.SimboloService;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
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

@SQLDelete(sql = "UPDATE simbolo SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/simbolo")
@Tag(name = "Simbolo",
        description = "Operações relacionadas ao gerenciamento e consulta dos Símbolo oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class SimboloController extends AbstractController<Simbolo, SimboloResponseDTO> {

    private final SimboloService simboloService;

    @Autowired
    public SimboloController(SimboloService simboloService) {
        this.simboloService = simboloService;
    }

    @Override
    protected List<Simbolo> getAllEntities() {
        return simboloService.getAll();
    }

    @Override
    protected Simbolo saveEntity(Simbolo entity) {
        return simboloService.register(entity);
    }

    @Override
    protected Simbolo updateEntity(String id, Simbolo entity) throws Exception {
        return simboloService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        simboloService.delete(id);
    }

    @Override
    protected Simbolo findEntityById(String id) {
        return simboloService.validarId(id);
    }

    @Override
    protected Simbolo toEntity(SimboloResponseDTO dto) {
        return new Simbolo(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected SimboloResponseDTO toResponseDTO(Simbolo entity, UserDetails userDetails) {
        SimboloResponseDTO dto = SimboloResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
