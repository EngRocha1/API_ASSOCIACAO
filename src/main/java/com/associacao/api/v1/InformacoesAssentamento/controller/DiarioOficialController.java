package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Cargo;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.DiarioOficial;
import com.associacao.api.v1.InformacoesAssentamento.dto.CargoResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.dto.DiarioOficialResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.CargoService;
import com.associacao.api.v1.InformacoesAssentamento.service.DiarioOficialService;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE cargo SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/diariooficial")
@Tag(name = "DiarioOficial",
        description = "Operações relacionadas ao gerenciamento e consulta dos Cargo oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class DiarioOficialController extends AbstractController<DiarioOficial, DiarioOficialResponseDTO> {

    private final DiarioOficialService diariooficialService;

    @Autowired
    public DiarioOficialController(DiarioOficialService diariooficialService) {
        this.diariooficialService = diariooficialService;
    }

    @Override
    protected List<DiarioOficial> getAllEntities() {
        return diariooficialService.getAll();
    }

    @Override
    protected DiarioOficial saveEntity(DiarioOficial entity) {
        return diariooficialService.register(entity);
    }

    @Override
    protected DiarioOficial updateEntity(String id, DiarioOficial entity) throws Exception {
        return diariooficialService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        diariooficialService.delete(id);
    }

    @Override
    protected DiarioOficial findEntityById(String id) {
        return diariooficialService.validarId(id);
    }

    @Override
    protected DiarioOficial toEntity(DiarioOficialResponseDTO dto) {
        return new DiarioOficial(
                dto.getId(),
                dto.getName(),
                dto.isAtivo(),
                dto.getDataPublicacao()
        );
    }

    @Override
    protected DiarioOficialResponseDTO toResponseDTO(DiarioOficial entity, UserDetails userDetails) {
        DiarioOficialResponseDTO dto = DiarioOficialResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .dataPublicacao(entity.getDataPublicacao())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
