package com.example.work3.v1.InformacoesAssentamento.controller;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.OrgaoGov;
import com.example.work3.v1.InformacoesAssentamento.dto.OrgaoGovResponseDTO;
import com.example.work3.v1.InformacoesAssentamento.service.OrgaoGovService;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
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

@SQLDelete(sql = "UPDATE orgaogov SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/orgaogov")
@Tag(name = "OrgaoGov",
        description = "Operações relacionadas ao gerenciamento e consulta dos Lotacao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class OrgaoGovController extends AbstractController<OrgaoGov, OrgaoGovResponseDTO> {

    private final OrgaoGovService orgaoGovService;

    @Autowired
    public OrgaoGovController(OrgaoGovService orgaoGovService) {
        this.orgaoGovService = orgaoGovService;
    }

    @Override
    protected List<OrgaoGov> getAllEntities() {
        return orgaoGovService.getAll();
    }

    @Override
    protected OrgaoGov saveEntity(OrgaoGov entity) {
        return orgaoGovService.register(entity);
    }

    @Override
    protected OrgaoGov updateEntity(String id, OrgaoGov entity) throws Exception {
        return orgaoGovService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        orgaoGovService.delete(id);
    }

    @Override
    protected OrgaoGov findEntityById(String id) {
        return orgaoGovService.validarId(id);
    }

    @Override
    protected OrgaoGov toEntity(OrgaoGovResponseDTO dto) {
        return new OrgaoGov(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected OrgaoGovResponseDTO toResponseDTO(OrgaoGov entity, UserDetails userDetails) {
        OrgaoGovResponseDTO dto = OrgaoGovResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
