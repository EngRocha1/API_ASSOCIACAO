package com.associacao.api.v1.Afastamentos.controller;

import com.associacao.api.v1.Afastamentos.mapper.AfastamentosMapper;
import com.associacao.api.v1.Afastamentos.domain.Afastamentos;
import com.associacao.api.v1.Afastamentos.domain.Listas.CID;
import com.associacao.api.v1.Afastamentos.domain.Listas.FluxoAprovacao;
import com.associacao.api.v1.Afastamentos.domain.Listas.Suspensao;
import com.associacao.api.v1.Afastamentos.domain.Listas.TipoAfastamento;
import com.associacao.api.v1.Afastamentos.dto.AfastamentosResponseDTO;
import com.associacao.api.v1.Afastamentos.service.*;
import com.example.work3.v1.Afastamentos.service.*;
import com.associacao.api.v1.Servidor.Service.ServidorService;
import com.associacao.api.v1.Servidor.domain.Servidor;
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
@SQLDelete(sql = "UPDATE afastamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/afastamentos")
@Tag(name = "Afastamentos",
        description = "Operações relacionadas ao gerenciamento e consulta das Afastamentos dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class AfastamentosController extends AbstractController<Afastamentos, AfastamentosResponseDTO> {

    private final AfastamentosService afastamentosService;
    private final ServidorService servidorService;
    private final TipoAfastamentoService tipoAfastamentoService;
    private final CIDService cIDService;
    private final SuspensaoService suspensaoService;
    private final FluxoAprovacaoService fluxoAprovacaoService;

    @Autowired
    public AfastamentosController(
            AfastamentosService afastamentosService,
            ServidorService servidorService,
            TipoAfastamentoService tipoAfastamentoService,
            CIDService cIDService,
            SuspensaoService suspensaoService,
            FluxoAprovacaoService fluxoAprovacaoService
    ) {
        this.afastamentosService = afastamentosService;
        this.servidorService = servidorService;
        this.tipoAfastamentoService = tipoAfastamentoService;
        this.cIDService = cIDService;
        this.suspensaoService = suspensaoService;
        this.fluxoAprovacaoService = fluxoAprovacaoService;
    }

    @Override
    protected List<Afastamentos> getAllEntities() {
        return afastamentosService.getAll();
    }

    @Override
    protected Afastamentos saveEntity(Afastamentos entity) {
        return afastamentosService.register(entity);
    }

    @Override
    protected Afastamentos updateEntity(String id, Afastamentos entity) throws Exception {
        return afastamentosService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        afastamentosService.delete(id);
    }

    @Override
    protected Afastamentos findEntityById(String id) {
        return afastamentosService.findById(id);
    }

    @Override
    protected Afastamentos toEntity(AfastamentosResponseDTO dto) {
        Servidor servidor = servidorService.validarId(dto.getServidorId());
        TipoAfastamento tipoAfastamento = tipoAfastamentoService.validarId(dto.getTipoAfastamento().getId());
        Suspensao suspensao = suspensaoService.validarId(dto.getSuspensao().getId());
        CID cid = cIDService.validarId(dto.getCid().getId());
        FluxoAprovacao fluxo = fluxoAprovacaoService.validarId(dto.getFluxoAprovacao().getId());


        return new Afastamentos
                (servidor,
                        cid,
                        fluxo,
                        tipoAfastamento,
                        suspensao,
                        dto.getDataReferencia(),
                        dto.getPeriodoInicio(),
                        dto.getPeriodoFim(),
                        dto.getStatusTipo(),
                        dto.getSolicitacaoTipo(),
                        dto.getRequerimentoTipo(),
                        dto.getSIAPITipo(),
                        dto.getJustificativa(),
                        dto.getNumeroSEI(),
                        dto.isAtivo()
        );
    }

    @Override
    protected AfastamentosResponseDTO toResponseDTO(Afastamentos entity, UserDetails userDetails) {
        AfastamentosResponseDTO dto = AfastamentosMapper.INSTANCE.toResponseDTO(entity);
        return populateAdminFields(dto, entity, userDetails);
    }

}
