package com.example.work3.v1.InformacoesAssentamento.controller;

import com.example.work3.v1.InformacoesAssentamento.domain.InformacoesAssentamento;
import com.example.work3.v1.InformacoesAssentamento.domain.Listas.*;
import com.example.work3.v1.InformacoesAssentamento.dto.InformacoesAssentamentoResponseDTO;
import com.example.work3.v1.InformacoesAssentamento.service.*;
import com.example.work3.v1.InformacoesAssentamento.InterfaceOpenAPI.InterfaceOpenAPI;
import com.example.work3.v1.Servidor.Service.ServidorService;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE informacoesassentamento SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/informacoesassentamento")
@Tag(name = "Informações de Assentamento",
        description = "Operações relacionadas ao gerenciamento e consulta das informações de assentamento dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class InformacoesAssentamentoController
        extends AbstractController<InformacoesAssentamento, InformacoesAssentamentoResponseDTO>
        implements InterfaceOpenAPI<InformacoesAssentamentoResponseDTO> {

    private final InformacoesAssentamentoService informacoesAssentamentoService;
    private final ServidorService servidorService;
    private final LotacaoService lotacaoService;
    private final DiarioOficialService diarioOficialService;
    private final OrgaoGovService orgaoGovService;
    private final SuperintendenciaService superintendenciaService;
    private final DiretoriaService diretoriaService;


    @Autowired
    public InformacoesAssentamentoController(
            InformacoesAssentamentoService informacoesAssentamentoService,
            ServidorService servidorService,
            LotacaoService lotacaoService,
            OrgaoGovService orgaoGovService,
            DiarioOficialService diarioOficialService,
            SuperintendenciaService superintendenciaService,
            DiretoriaService diretoriaService
    ) {
        this.informacoesAssentamentoService = informacoesAssentamentoService;
        this.servidorService = servidorService;
        this.lotacaoService = lotacaoService;
        this.diarioOficialService = diarioOficialService;
        this.orgaoGovService = orgaoGovService;
        this.superintendenciaService = superintendenciaService;
        this.diretoriaService = diretoriaService;
    }

    @Override
    protected List<InformacoesAssentamento> getAllEntities() {
        return informacoesAssentamentoService.getAll();
    }

    @Override
    protected InformacoesAssentamento saveEntity(InformacoesAssentamento entity) {
        return informacoesAssentamentoService.register(entity);
    }

    @Override
    protected InformacoesAssentamento updateEntity(String id, InformacoesAssentamento entity) throws Exception {
        return informacoesAssentamentoService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        informacoesAssentamentoService.delete(id);
    }

    @Override
    protected InformacoesAssentamento findEntityById(String id) {
        return informacoesAssentamentoService.findById(id);
    }

    @Override
    protected InformacoesAssentamento toEntity(InformacoesAssentamentoResponseDTO dto) {
        Servidor servidor = servidorService.validarId(dto.getServidorId());
        Lotacao lotacao = lotacaoService.validarId(dto.getLotacaoId());
        DiarioOficial diarioOficial = diarioOficialService.validarId(dto.getDiariooficialId());
        Superintendencia superintendencia = superintendenciaService.validarId(dto.getSuperintendenciaId());
        OrgaoGov orgaoGov = orgaoGovService.validarId(dto.getOrgaogovId());
        Diretoria diretoria = diretoriaService.validarId(dto.getDiretoriaId());

        return new InformacoesAssentamento(
                servidor,
                lotacao,
                diarioOficial,
                superintendencia,
                orgaoGov,
                diretoria,
                dto.getMatriculaInstitucional(),
                dto.getEmailInstitucionalGov(),
                dto.isAtivo()
        );
    }


    @Override
    protected InformacoesAssentamentoResponseDTO toResponseDTO(InformacoesAssentamento entity, UserDetails userDetails) {
        InformacoesAssentamentoResponseDTO dto = InformacoesAssentamentoResponseDTO.builder()
                .id(entity.getId())
                .lotacaoId(entity.getLotacao().getId())
                .lotacaoNome(entity.getLotacao().getName())
                .lotacaoDescricao(entity.getLotacao().getDescricao())
                .diariooficialId(entity.getDiariooficial().getId())
                .diariooficialNome(entity.getDiariooficial().getName())
                .diariooficialDescricao(entity.getDiariooficial().getDescricao())
                .diariooficialVinculo(entity.getDiariooficial().getVinculo().toString())
                .diariooficialSimbolo(entity.getDiariooficial().getSimbolo().toString())
                .diariooficialDataEfeito(entity.getDiariooficial().getDataEfeito().toString())
                .diariooficialDataPublicacao(entity.getDiariooficial().getDataPublicacao().toString())
                .superintendenciaId(entity.getSuperintendencia().getId())
                .superintendenciaNome(entity.getSuperintendencia().getName())
                .superintendenciaDescricao(entity.getSuperintendencia().getDescricao())
                .diretoriaId(entity.getDiretoria().getId())
                .diretoriaNome(entity.getDiretoria().getName())
                .diretoriaDescricao(entity.getDiretoria().getDescricao())
                .orgaogovId(entity.getOrgaogov().getId())
                .orgaogovNome(entity.getOrgaogov().getName())
                .orgaogovDescricao(entity.getOrgaogov().getDescricao())
                .servidorId(entity.getServidor().getId())
                .servidorNome(entity.getServidor().getName())
                .matriculaInstitucional(entity.getMatriculaInstitucional())
                .EmailInstitucionalGov(entity.getEmailInstitucionalGov())
                .ativo(entity.isAtivo())
                .build();
        return populateAdminFields(dto, entity, userDetails);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/byServidorId/{servidorId}")
    public ResponseEntity<InformacoesAssentamentoResponseDTO> getInformacoesAssentamentoByServidorId(
            @PathVariable String servidorId,
            @AuthenticationPrincipal UserDetails userDetails) {
        InformacoesAssentamento informacoesAssentamento = informacoesAssentamentoService.findByServidorId(servidorId);
        if (informacoesAssentamento == null) {
            return ResponseEntity.noContent().build();
        }
        InformacoesAssentamentoResponseDTO dto = toResponseDTO(informacoesAssentamento, userDetails);
        return ResponseEntity.ok(dto);
    }


}
