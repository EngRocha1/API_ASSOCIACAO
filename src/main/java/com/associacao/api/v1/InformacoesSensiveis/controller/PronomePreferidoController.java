package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.PronomePreferidoService;
import com.associacao.api.v1.InformacoesSensiveis.dto.PronomePreferidoResponseDTO;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.PronomePreferido;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
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
@SQLDelete(sql = "UPDATE pronomepreferido SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/pronomepreferido")
@Tag(name = "Pronome Preferido",
        description = "Operações relacionadas ao gerenciamento e consulta dos pronomes preferidos dos usuários, incluindo criação, atualização, listagem e exclusão.")


public class PronomePreferidoController extends AbstractController<PronomePreferido, PronomePreferidoResponseDTO> {

    private final PronomePreferidoService pronomePreferidoService;

    @Autowired
    public PronomePreferidoController(PronomePreferidoService pronomePreferidoService) {
        this.pronomePreferidoService = pronomePreferidoService;
    }

    @Override
    protected List<PronomePreferido> getAllEntities() {
        return pronomePreferidoService.getAll();
    }

    @Override
    protected PronomePreferido saveEntity(PronomePreferido entity) {
        return pronomePreferidoService.register(entity);
    }

    @Override
    protected PronomePreferido updateEntity(String id, PronomePreferido entity) throws Exception {
        return pronomePreferidoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        pronomePreferidoService.delete(id);
    }

    @Override
    protected PronomePreferido findEntityById(String id) {
        return pronomePreferidoService.validarId(id);
    }

    @Override
    protected PronomePreferido toEntity(PronomePreferidoResponseDTO dto) {
        return new PronomePreferido(dto.getId(),dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected PronomePreferidoResponseDTO toResponseDTO(PronomePreferido entity, UserDetails userDetails) {
        PronomePreferidoResponseDTO dto = new PronomePreferidoResponseDTO(entity.getId(),entity.getName(), entity.getDescricao(), entity.isAtivo());
        return populateAdminFields(dto, entity, userDetails);
    }
}
