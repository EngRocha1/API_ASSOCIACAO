package com.example.work3.v1.InformacoesAssentamento.controller;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Cargo;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.DiarioOficial;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Simbolo;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Vinculo;
import com.example.work3.v1.InformacoesAssentamento.dto.DiarioOficialResponseDTO;
import com.example.work3.v1.InformacoesAssentamento.service.CargoService;
import com.example.work3.v1.InformacoesAssentamento.service.DiarioOficialService;
import com.example.work3.v1.InformacoesAssentamento.service.SimboloService;
import com.example.work3.v1.InformacoesAssentamento.service.VinculoService;
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
@SQLDelete(sql = "UPDATE diariooficial SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/diariooficial")
@Tag(name = "Diario Oficial",
        description = "Operações relacionadas ao gerenciamento e consulta dos Lotacao oferecidos, incluindo criação, atualização, listagem e exclusão.")

public class DiarioOficialController extends AbstractController<DiarioOficial, DiarioOficialResponseDTO> {

    private final DiarioOficialService diariooficialService;
    private final SimboloService simboloService;
    private final VinculoService vinculoService;
    private final CargoService cargoService;

    @Autowired
    public DiarioOficialController(
            DiarioOficialService diariooficialService,
            SimboloService simboloService,
            VinculoService vinculoService,
            CargoService cargoService) {
        this.diariooficialService = diariooficialService;
        this.simboloService = simboloService;
        this.vinculoService = vinculoService;
        this.cargoService = cargoService;
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
        Simbolo simbolo = simboloService.validarId(dto.getSimboloId());
        Vinculo vinculo = vinculoService.validarId(dto.getVinculoId());
        Cargo cargo = cargoService.validarId(dto.getCargoId());
        DiarioOficial.NomeacaoExoneracao tipo = DiarioOficial.NomeacaoExoneracao.valueOf(dto.getNomeacaoExoneracao());

        return new DiarioOficial(
                dto.getId(),
                dto.getName(),
                tipo,
                dto.isAtivo(),
                simbolo,
                vinculo,
                cargo,
                dto.getDataEfeito(),
                dto.getDataPublicacao()
        );
    }



    @Override
    protected DiarioOficialResponseDTO toResponseDTO(DiarioOficial entity, UserDetails userDetails) {
        DiarioOficialResponseDTO dto = DiarioOficialResponseDTO.builder()
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
                .DataPublicacao(entity.getDataPublicacao())
                .DataEfeito(entity.getDataEfeito())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }
}
