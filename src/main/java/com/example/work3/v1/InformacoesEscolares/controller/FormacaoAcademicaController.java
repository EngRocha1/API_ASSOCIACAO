package com.example.work3.v1.InformacoesEscolares.controller;

import com.example.work3.v1.InformacoesEscolares.Service.FormacaoAcademicaService;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.FormacaoAcademica;
import com.example.work3.v1.InformacoesEscolares.dto.FormacaoAcademicaResponseDTO;
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

@SQLDelete(sql = "UPDATE formacaoacademica SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/formacaoacademica")
@Tag(name = "Formação Acadêmica",
        description = "Operações relacionadas ao gerenciamento e consulta das formações acadêmicas dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class FormacaoAcademicaController extends AbstractController<FormacaoAcademica, FormacaoAcademicaResponseDTO> {

    private final FormacaoAcademicaService formacaoAcademicaService;

    @Autowired
    public FormacaoAcademicaController(FormacaoAcademicaService formacaoAcademicaService) {
        this.formacaoAcademicaService = formacaoAcademicaService;
    }

    @Override
    protected List<FormacaoAcademica> getAllEntities() {
        return formacaoAcademicaService.getAll();
    }

    @Override
    protected FormacaoAcademica saveEntity(FormacaoAcademica entity) {
        return formacaoAcademicaService.register(entity);
    }

    @Override
    protected FormacaoAcademica updateEntity(String id, FormacaoAcademica entity) throws Exception {
        return formacaoAcademicaService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        formacaoAcademicaService.delete(id);
    }

    @Override
    protected FormacaoAcademica findEntityById(String id) {
        return formacaoAcademicaService.validarId(id);
    }

    @Override
    protected FormacaoAcademica toEntity(FormacaoAcademicaResponseDTO dto) {
        return new FormacaoAcademica(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected FormacaoAcademicaResponseDTO toResponseDTO(FormacaoAcademica entity, UserDetails userDetails) {
        FormacaoAcademicaResponseDTO dto = FormacaoAcademicaResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
