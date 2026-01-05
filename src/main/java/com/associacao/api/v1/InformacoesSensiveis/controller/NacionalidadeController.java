package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.NacionalidadeService;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Nacionalidade;
import com.associacao.api.v1.InformacoesSensiveis.dto.NacionalidadeResponseDTO;
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
@SQLDelete(sql = "UPDATE nacionalidades SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/nacionalidades")
@Tag(name = "Nacionalidades", description = "Operações relacionadas ao gerenciamento  e consulta de nacionalidades, incluindo criação, atualização, listagem e exclusão.")
public class NacionalidadeController extends AbstractController<Nacionalidade, NacionalidadeResponseDTO> {

    private final NacionalidadeService nacionalidadeService;

    @Autowired
    public NacionalidadeController(NacionalidadeService nacionalidadeService) {
        this.nacionalidadeService = nacionalidadeService;
    }

    @Override
    protected List<Nacionalidade> getAllEntities() {
        return nacionalidadeService.getAll();
    }

    @Override
    protected Nacionalidade saveEntity(@RequestBody @Valid Nacionalidade entity) {
        return nacionalidadeService.register(entity);
    }

    @Override
    protected Nacionalidade updateEntity(@PathVariable String id, @RequestBody @Valid Nacionalidade entity) throws Exception {
        return nacionalidadeService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        nacionalidadeService.delete(id);
    }

    @Override
    protected Nacionalidade findEntityById(@PathVariable String id) {
        return nacionalidadeService.validarId(id);
    }

    @Override
    protected Nacionalidade toEntity(NacionalidadeResponseDTO dto) {
        return new Nacionalidade(dto.getId(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected NacionalidadeResponseDTO toResponseDTO(Nacionalidade entity, UserDetails userDetails) {
        NacionalidadeResponseDTO dto = new NacionalidadeResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}