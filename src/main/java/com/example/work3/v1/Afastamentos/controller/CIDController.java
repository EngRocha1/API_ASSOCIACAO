package com.example.work3.v1.Afastamentos.controller;

import com.example.work3.v1.Afastamentos.domain.Listas.CID;
import com.example.work3.v1.Afastamentos.dto.CIDResponseDTO;
import com.example.work3.v1.Afastamentos.service.CIDService;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SQLDelete(sql = "UPDATE cid SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/cid")
@Tag(name = "CID",
        description = "Operações relacionadas ao gerenciamento e consulta dos Tipo de CID oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class CIDController extends AbstractController<CID, CIDResponseDTO> {

    private final CIDService cidService;

    @Autowired
    public CIDController(CIDService cidService) {
        this.cidService = cidService;
    }

    @Override
    protected List<CID> getAllEntities() {
        return cidService.getAll();
    }

    @Override
    protected CID saveEntity(CID entity) {
        return cidService.register(entity);
    }

    @Override
    protected CID updateEntity(String id, CID entity) throws Exception {
        return cidService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        cidService.delete(id);
    }

    @Override
    protected CID findEntityById(String id) {
        return cidService.validarId(id);
    }

    @Override
    protected CID toEntity(CIDResponseDTO dto) {
        return new CID(dto.getCodigo(), dto.getName(), dto.getDescricao(), dto.isAtivo());
    }

    @Override
    protected CIDResponseDTO toResponseDTO(CID entity, UserDetails userDetails) {
        CIDResponseDTO dto = CIDResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .name(entity.getName())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/atualizar-cids")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> atualizarCIDs() {
        cidService.atualizarCIDs();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca CIDs por termo (código, nome ou descrição)",
            description = "Retorna uma lista de CIDs que correspondem ao termo de busca. O termo deve ter no mínimo 2 caracteres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CIDs encontrados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CIDResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (termo muito curto)",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<List<CIDResponseDTO>> searchCIDs(
            @RequestParam(required = true) String term) {
        try {
            List<CID> cids = cidService.searchCIDs(term);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<CIDResponseDTO> dtoList = cids.stream()
                    .map(cid -> toResponseDTO(cid, userDetails))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor ao buscar CIDs.", e);
        }
    }


}
