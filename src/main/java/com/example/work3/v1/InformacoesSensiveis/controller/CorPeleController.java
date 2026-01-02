package com.example.work3.v1.InformacoesSensiveis.controller;

import com.example.work3.v1.InformacoesSensiveis.Service.CorPeleService;
import com.example.work3.v1.InformacoesSensiveis.dto.CorPeleResponseDTO;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.CorPele;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

@SQLDelete(sql = "UPDATE corpele SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/corpele")
@Tag(name = "Cor da Pele",
        description = "Operações relacionadas ao gerenciamento e consulta das cores de pele dos usuários, incluindo criação, atualização, listagem e exclusão.")
public class CorPeleController extends AbstractController<CorPele, CorPeleResponseDTO>  {

    private final CorPeleService corPeleService;

    @Autowired
    public CorPeleController(CorPeleService corPeleService) {
        this.corPeleService = corPeleService;
    }

    @Override
    protected List<CorPele> getAllEntities() {
        return corPeleService.getAll();
    }

    @Override
    protected CorPele saveEntity(@RequestBody @Valid CorPele entity) {
        return corPeleService.register(entity);
    }

    @Override
    protected CorPele updateEntity(@PathVariable String id, @RequestBody @Valid CorPele entity) throws Exception {
        return corPeleService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(@PathVariable String id) {
        corPeleService.delete(id);
    }

    @Override
    protected CorPele findEntityById(@PathVariable String id) {
        return corPeleService.validarId(id);
    }

    @Override
    protected CorPele toEntity(CorPeleResponseDTO dto) {
        return new CorPele(dto.getId(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected CorPeleResponseDTO toResponseDTO(CorPele entity, UserDetails userDetails) {
        CorPeleResponseDTO dto = new CorPeleResponseDTO(entity.getId(), entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }


}
