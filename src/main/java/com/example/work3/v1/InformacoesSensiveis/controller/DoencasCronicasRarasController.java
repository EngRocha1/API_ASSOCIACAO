package com.example.work3.v1.InformacoesSensiveis.controller;

import com.example.work3.v1.InformacoesSensiveis.Service.DoencasCronicasRarasService;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.DoencasCronicasRaras;
import com.example.work3.v1.InformacoesSensiveis.dto.DoencasCronicasRarasResponseDTO;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE doencascronicasraras SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/doencascronicasraras")
@Tag(name = "Doenças Crônicas/Raras",
        description = "Operações relacionadas ao gerenciamento e consulta de doenças crônicas ou raras, incluindo criação, atualização, listagem e exclusão.")
public class DoencasCronicasRarasController extends AbstractController<DoencasCronicasRaras, DoencasCronicasRarasResponseDTO> {

    private final DoencasCronicasRarasService doencasCronicasRarasService;

    @Autowired
    public DoencasCronicasRarasController(DoencasCronicasRarasService doencasCronicasRarasService) {
        this.doencasCronicasRarasService = doencasCronicasRarasService;
    }

    @Override
    protected List<DoencasCronicasRaras> getAllEntities() {
        return doencasCronicasRarasService.getAll();
    }

    @Override
    protected DoencasCronicasRaras saveEntity(@RequestBody @Valid DoencasCronicasRaras entity) {
        return doencasCronicasRarasService.register(entity);
    }

    @Override
    protected DoencasCronicasRaras updateEntity(@PathVariable String id, @RequestBody @Valid DoencasCronicasRaras entity) throws Exception {
        return doencasCronicasRarasService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        doencasCronicasRarasService.delete(id);
    }

    @Override
    protected DoencasCronicasRaras findEntityById(@PathVariable String id) {
        return doencasCronicasRarasService.validarId(id);
    }

    @Override
    protected DoencasCronicasRaras toEntity(DoencasCronicasRarasResponseDTO dto) {
        return new DoencasCronicasRaras(dto.getId(),dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected DoencasCronicasRarasResponseDTO toResponseDTO(DoencasCronicasRaras entity, UserDetails userDetails) {
        DoencasCronicasRarasResponseDTO dto = new DoencasCronicasRarasResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}