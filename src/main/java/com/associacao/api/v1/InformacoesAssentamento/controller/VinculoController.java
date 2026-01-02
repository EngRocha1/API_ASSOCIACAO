package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Vinculo;
import com.associacao.api.v1.InformacoesAssentamento.dto.VinculoResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.VinculoService;
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
@SQLDelete(sql = "UPDATE vinculo SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/vinculo")
@Tag(name = "Vinculo",
        description = "Operações relacionadas ao gerenciamento e consulta dos Vínculo oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class VinculoController extends AbstractController<Vinculo, VinculoResponseDTO> {

    private final VinculoService vinculoService;

    @Autowired
    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @Override
    protected List<Vinculo> getAllEntities() {
        return vinculoService.getAll();
    }

    @Override
    protected Vinculo saveEntity(Vinculo entity) {
        return vinculoService.register(entity);
    }

    @Override
    protected Vinculo updateEntity(String id, Vinculo entity) throws Exception {
        return vinculoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        vinculoService.delete(id);
    }

    @Override
    protected Vinculo findEntityById(String id) {
        return vinculoService.validarId(id);
    }

    @Override
    protected Vinculo toEntity(VinculoResponseDTO dto) {
        return new Vinculo(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected VinculoResponseDTO toResponseDTO(Vinculo entity, UserDetails userDetails) {
        VinculoResponseDTO dto = VinculoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
