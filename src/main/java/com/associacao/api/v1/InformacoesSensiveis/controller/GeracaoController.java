package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.GeracaoService;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Geracao;
import com.associacao.api.v1.InformacoesSensiveis.dto.GeracaoResponseDTO;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
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
@SQLDelete(sql = "UPDATE geracoes SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/geracoes")
@Tag(name = "Geração",
        description = "Operações relacionadas ao gerenciamento e consulta das gerações (Baby Boomer, Geração X, etc.), incluindo criação, atualização, listagem e exclusão.")
public class GeracaoController extends AbstractController<Geracao, GeracaoResponseDTO> {

    private final GeracaoService geracaoService;

    @Autowired
    public GeracaoController(GeracaoService geracaoService) {
        this.geracaoService = geracaoService;
    }

    @Override
    protected List<Geracao> getAllEntities() {
        return geracaoService.getAll();
    }

    @Override
    protected Geracao saveEntity(@RequestBody @Valid Geracao entity) {
        return geracaoService.register(entity);
    }

    @Override
    protected Geracao updateEntity(@PathVariable String id, @RequestBody @Valid Geracao entity) throws Exception {
        return geracaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        geracaoService.delete(id);
    }

    @Override
    protected Geracao findEntityById(@PathVariable String id) {
        return geracaoService.validarId(id);
    }

    @Override
    protected Geracao toEntity(GeracaoResponseDTO dto) {
        return new Geracao(dto.getId(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected GeracaoResponseDTO toResponseDTO(Geracao entity, UserDetails userDetails) {
        GeracaoResponseDTO dto = new GeracaoResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}