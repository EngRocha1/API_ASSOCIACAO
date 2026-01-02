package com.example.work3.v1.InformacoesSensiveis.controller;

import com.example.work3.v1.InformacoesSensiveis.Service.DeficienciasService;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Deficiencias;
import com.example.work3.v1.InformacoesSensiveis.dto.DeficienciasResponseDTO;
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

@SQLDelete(sql = "UPDATE deficiencias SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/deficiencias")
@Tag(name = "Deficiências",
        description = "Operações relacionadas ao gerenciamento e consulta das deficiência dos usuários, incluindo criação, atualização, listagem e exclusão.")
public class DeficienciasController extends AbstractController<Deficiencias, DeficienciasResponseDTO>  {

    private final DeficienciasService deficienciasService;

    @Autowired
    public DeficienciasController(DeficienciasService deficienciasService) {
        this.deficienciasService = deficienciasService;
    }

    @Override
    protected List<Deficiencias> getAllEntities() {
        return deficienciasService.getAll();
    }

    @Override
    protected Deficiencias saveEntity(@RequestBody @Valid Deficiencias entity) {
        return deficienciasService.register(entity);
    }

    @Override
    protected Deficiencias updateEntity(@PathVariable String id, @RequestBody @Valid Deficiencias entity) throws Exception {
        return deficienciasService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        deficienciasService.delete(id);
    }

    @Override
    protected Deficiencias findEntityById(@PathVariable String id) {
        return deficienciasService.validarId(id);
    }

    @Override
    protected Deficiencias toEntity(DeficienciasResponseDTO dto) {
        return new Deficiencias(dto.getId(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected DeficienciasResponseDTO toResponseDTO(Deficiencias entity, UserDetails userDetails) {
        DeficienciasResponseDTO dto = new DeficienciasResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }


}
