package com.associacao.api.v1.InformacoesAssentamento.controller;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.*;
import com.associacao.api.v1.InformacoesAssentamento.dto.AtoMovimentacaoResponseDTO;
import com.associacao.api.v1.InformacoesAssentamento.service.*;
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
@SQLDelete(sql = "UPDATE atomvimentacao SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/atomvimentacao")
@Tag(name = "AtoMovimentacao",
        description = "Operações relacionadas ao gerenciamento e consulta dos Atos de Movimentação oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class AtoMovimentacaoController extends AbstractController<AtoMovimentacao, AtoMovimentacaoResponseDTO> {

    private final AtoMovimentacaoService atomvimentacaoService;
    private final SimboloService simboloService;
    private final VinculoService vinculoService;
    private final CargoService cargoService;
    private final DiarioOficialService diariooficialService;

    @Autowired
    public AtoMovimentacaoController(
            AtoMovimentacaoService atomvimentacaoService,
            SimboloService simboloService,
            VinculoService vinculoService,
            CargoService cargoService,
            DiarioOficialService diariooficialService) {
        this.atomvimentacaoService = atomvimentacaoService;
        this.simboloService = simboloService;
        this.vinculoService = vinculoService;
        this.cargoService = cargoService;
        this.diariooficialService = diariooficialService;
    }

    @Override
    protected List<AtoMovimentacao> getAllEntities() {
        return atomvimentacaoService.getAll();
    }

    @Override
    protected AtoMovimentacao saveEntity(AtoMovimentacao entity) {
        return atomvimentacaoService.register(entity);
    }

    @Override
    protected AtoMovimentacao updateEntity(String id, AtoMovimentacao entity) throws Exception {
        return atomvimentacaoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        atomvimentacaoService.delete(id);
    }

    @Override
    protected AtoMovimentacao findEntityById(String id) {
        return atomvimentacaoService.validarId(id);
    }

    @Override
    protected AtoMovimentacao toEntity(AtoMovimentacaoResponseDTO dto) {
        Simbolo simbolo = simboloService.validarId(dto.getSimboloId());
        Vinculo vinculo = vinculoService.validarId(dto.getVinculoId());
        Cargo cargo = cargoService.validarId(dto.getCargoId());
        DiarioOficial diariooficial = diariooficialService.validarId(dto.getDiariooficial().getId());
        AtoMovimentacao.NomeacaoExoneracao tipo = AtoMovimentacao.NomeacaoExoneracao.valueOf(dto.getNomeacaoExoneracao());

        return new AtoMovimentacao(
                dto.getId(),
                dto.getName(),
                tipo,
                dto.isAtivo(),
                simbolo,
                vinculo,
                cargo,
                dto.getDataEfeito(),
                diariooficial
        );
    }



    @Override
    protected AtoMovimentacaoResponseDTO toResponseDTO(AtoMovimentacao entity, UserDetails userDetails) {
        AtoMovimentacaoResponseDTO dto = AtoMovimentacaoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .NomeacaoExoneracao(entity.getTipo().toString())
                .ativo(entity.isAtivo())
                .simboloId(entity.getSimbolo().getId())
                .simboloNome(entity.getSimbolo().getName())
                .simboloDescricao(entity.getSimbolo().getDescricao())
                .vinculoId(entity.getVinculo().getId())
                .vinculoNome(entity.getVinculo().getName())
                .vinculoDescricao(entity.getVinculo().getDescricao())
                .cargoId(entity.getCargo().getId())
                .cargoNome(entity.getCargo().getName())
                .cargoDescricao(entity.getCargo().getDescricao())
                .diariooficial(entity.getDiariooficial())
                .DataEfeito(entity.getDataEfeito())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
