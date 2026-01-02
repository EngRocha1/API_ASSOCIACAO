package com.example.work3.v1.InformacoesSensiveis.controller;

import com.example.work3.v1.InformacoesSensiveis.Service.RacaEtniaService;
import com.example.work3.v1.InformacoesSensiveis.dto.RacaEtniaResponseDTO;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.RacaEtnia;
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
@SQLDelete(sql = "UPDATE raca_etnia SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/racaetnia")
@Tag(name = "Etnia",
        description = "Operações relacionadas ao gerenciamento e consulta das etnias dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class RacaEtniaController extends AbstractController<RacaEtnia, RacaEtniaResponseDTO> {

    private final RacaEtniaService racaEtniaService;

    @Autowired
    public RacaEtniaController(RacaEtniaService racaEtniaService) {
        this.racaEtniaService = racaEtniaService;
    }

    @Override
    protected List<RacaEtnia> getAllEntities() {
        return racaEtniaService.getAll();
    }

    @Override
    protected RacaEtnia saveEntity(RacaEtnia entity) {
        return racaEtniaService.register(entity);
    }

    @Override
    protected RacaEtnia updateEntity(String id, RacaEtnia entity) throws Exception {
        return racaEtniaService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        racaEtniaService.delete(id);
    }

    @Override
    protected RacaEtnia findEntityById(String id) {
        return racaEtniaService.validarId(id);
    }

    @Override
    protected RacaEtnia toEntity(RacaEtniaResponseDTO dto) {
        return new RacaEtnia(dto.getId(),dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected RacaEtniaResponseDTO toResponseDTO(RacaEtnia entity, UserDetails userDetails) {
        RacaEtniaResponseDTO dto = new RacaEtniaResponseDTO(entity.getId(),entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}
