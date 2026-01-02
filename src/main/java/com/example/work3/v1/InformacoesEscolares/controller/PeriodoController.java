package com.example.work3.v1.InformacoesEscolares.controller;

import com.example.work3.v1.InformacoesEscolares.Service.PeriodoService;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.Periodo;
import com.example.work3.v1.InformacoesEscolares.dto.PeriodoResponseDTO;
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

@SQLDelete(sql = "UPDATE periodododia SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/periodododia")
@Tag(name = "Período do Dia",
        description = "Operações relacionadas ao gerenciamento e consulta dos períodos do dia, incluindo criação, atualização, listagem e exclusão.")

public class PeriodoController extends AbstractController<Periodo, PeriodoResponseDTO> {

    private final PeriodoService periodoService;

    @Autowired
    public PeriodoController(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    @Override
    protected List<Periodo> getAllEntities() {
        return periodoService.getAll();
    }

    @Override
    protected Periodo saveEntity(Periodo entity) {
        return periodoService.register(entity);
    }

    @Override
    protected Periodo updateEntity(String id, Periodo entity) throws Exception {
        return periodoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        periodoService.delete(id);
    }

    @Override
    protected Periodo findEntityById(String id) {
        return periodoService.validarId(id);
    }

    @Override
    protected Periodo toEntity(PeriodoResponseDTO dto) {
        return new Periodo(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected PeriodoResponseDTO toResponseDTO(Periodo entity, UserDetails userDetails) {
        PeriodoResponseDTO dto = PeriodoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
