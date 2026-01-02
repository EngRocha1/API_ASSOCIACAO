package com.associacao.api.v1.DocumentosPessoais.controller;

import com.associacao.api.v1.DocumentosPessoais.domain.Listas.TipoDeDocumento;
import com.associacao.api.v1.DocumentosPessoais.dto.TipoDeDocumentoResponseDTO;
import com.associacao.api.v1.DocumentosPessoais.service.TipoDeDocumentoService;
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
@SQLDelete(sql = "UPDATE tipodedocumento SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/tipodedocumento")
@Tag(name = "Tipo De Documento",
        description = "Operações relacionadas ao gerenciamento e consulta dos Tipo De Documento oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class TipoDeDocumentoController extends AbstractController<TipoDeDocumento, TipoDeDocumentoResponseDTO> {

    private final TipoDeDocumentoService TipoDeDocumentoService;

    @Autowired
    public TipoDeDocumentoController(TipoDeDocumentoService TipoDeDocumentoService) {
        this.TipoDeDocumentoService = TipoDeDocumentoService;
    }

    @Override
    protected List<TipoDeDocumento> getAllEntities() {
        return TipoDeDocumentoService.getAll();
    }

    @Override
    protected TipoDeDocumento saveEntity(TipoDeDocumento entity) {
        return TipoDeDocumentoService.register(entity);
    }

    @Override
    protected TipoDeDocumento updateEntity(String id, TipoDeDocumento entity) throws Exception {
        return TipoDeDocumentoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        TipoDeDocumentoService.delete(id);
    }

    @Override
    protected TipoDeDocumento findEntityById(String id) {
        return TipoDeDocumentoService.validarId(id);
    }

    @Override
    protected TipoDeDocumento toEntity(TipoDeDocumentoResponseDTO dto) {
        return new TipoDeDocumento(dto.getId(), dto.getName(), dto.isAtivo());
    }

    @Override
    protected TipoDeDocumentoResponseDTO toResponseDTO(TipoDeDocumento entity, UserDetails userDetails) {
        TipoDeDocumentoResponseDTO dto = TipoDeDocumentoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
