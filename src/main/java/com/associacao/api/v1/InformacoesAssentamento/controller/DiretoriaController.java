package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Diretoria;
import com.associacao.api.v1.InformacoesAssentamento.dto.DiretoriaResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.DiretoriaService;
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
@SQLDelete(sql = "UPDATE diretoria SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/diretoria")
@Tag(name = "Diretoria",
        description = "Operações relacionadas ao gerenciamento e consulta dos Lotacao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class DiretoriaController extends AbstractController<Diretoria, DiretoriaResponseDTO> {

    private final DiretoriaService diretoriaService;

    @Autowired
    public DiretoriaController(DiretoriaService diretoriaService) {
        this.diretoriaService = diretoriaService;
    }

    @Override
    protected List<Diretoria> getAllEntities() {
        return diretoriaService.getAll();
    }

    @Override
    protected Diretoria saveEntity(Diretoria entity) {
        return diretoriaService.register(entity);
    }

    @Override
    protected Diretoria updateEntity(String id, Diretoria entity) throws Exception {
        return diretoriaService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        diretoriaService.delete(id);
    }

    @Override
    protected Diretoria findEntityById(String id) {
        return diretoriaService.validarId(id);
    }

    @Override
    protected Diretoria toEntity(DiretoriaResponseDTO dto) {
        return new Diretoria(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected DiretoriaResponseDTO toResponseDTO(Diretoria entity, UserDetails userDetails) {
        DiretoriaResponseDTO dto = DiretoriaResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
