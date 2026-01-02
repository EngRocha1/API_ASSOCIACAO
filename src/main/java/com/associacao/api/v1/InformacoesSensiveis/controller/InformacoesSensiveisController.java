package com.associacao.api.v1.InformacoesSensiveis.controller;

import com.associacao.api.v1.InformacoesSensiveis.Service.InformacoesSensiveisService;
import com.associacao.api.v1.InformacoesSensiveis.dto.InformacoesSensiveisResponseDTO;
import com.associacao.api.v1.InformacoesSensiveis.domain.InformacoesSensiveis;
import com.associacao.api.v1.SuperClasses.controller.AbstractController;
import com.associacao.api.v1.InformacoesSensiveis.InterfaceOpenAPI.InterfaceOpenAPI; // Verifique se esta interface é realmente necessária e se ela ainda se alinha após as mudanças
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@SQLDelete(sql = "UPDATE informacoessensiveis SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/informacoessensiveis")
@Tag(name = "Informações Sensíveis",
        description = "Operações relacionadas ao gerenciamento e consulta de informações sensíveis dos usuários, incluindo criação, atualização, listagem e exclusão.")

public class InformacoesSensiveisController extends AbstractController<InformacoesSensiveis, InformacoesSensiveisResponseDTO> implements InterfaceOpenAPI<InformacoesSensiveisResponseDTO> {

    private final InformacoesSensiveisService informacoesSensiveisService;

    @Autowired
    public InformacoesSensiveisController(
            InformacoesSensiveisService informacoesSensiveisService
    ) {
        this.informacoesSensiveisService = informacoesSensiveisService;
    }

    @Override
    protected List<InformacoesSensiveis> getAllEntities() {
        return informacoesSensiveisService.getAll();
    }

    @Override
    protected InformacoesSensiveis saveEntity(InformacoesSensiveis entity) {
        return informacoesSensiveisService.register(entity);
    }

    @Override
    protected InformacoesSensiveis updateEntity(String id, InformacoesSensiveis entity) throws Exception {
        return informacoesSensiveisService.update(id, entity);
    }

    @Override
    @Transactional
    protected void deleteEntity(String id) {
        informacoesSensiveisService.delete(id);
    }

    @Override
    protected InformacoesSensiveis findEntityById(String id) {
        return informacoesSensiveisService.findById(id);
    }


    @Override
    protected InformacoesSensiveis toEntity(@Valid InformacoesSensiveisResponseDTO dto) {
        InformacoesSensiveis entity = new InformacoesSensiveis();

        entity.setNomeSocial(dto.getNomeSocial());

        if (dto.getNomeSocial() != null && !dto.getNomeSocial().isEmpty()) {
            entity.setNomeSocial(dto.getNomeSocial());
        }

        entity.setAtivo(dto.isAtivo());


        if (dto.getServidorId() != null && !dto.getServidorId().isEmpty()) {
            entity.setServidor(informacoesSensiveisService.getServidorService().validarId(dto.getServidorId()));
        }
        if (dto.getGeneroId() != null && !dto.getGeneroId().isEmpty()) {
            entity.setGenero(informacoesSensiveisService.getGeneroService().validarId(dto.getGeneroId()));
        }
        if (dto.getOrientacaoSexualId() != null && !dto.getOrientacaoSexualId().isEmpty()) {
            entity.setOrientacaoSexual(informacoesSensiveisService.getOrientacaoSexualService().validarId(dto.getOrientacaoSexualId()));
        }
        if (dto.getEstadoCivilId() != null && !dto.getEstadoCivilId().isEmpty()) {
            entity.setEstadoCivil(informacoesSensiveisService.getEstadoCivilService().validarId(dto.getEstadoCivilId()));
        }
        if (dto.getRacaEtniaId() != null && !dto.getRacaEtniaId().isEmpty()) {
            entity.setRacaEtnia(informacoesSensiveisService.getRacaEtniaService().validarId(dto.getRacaEtniaId()));
        }
        if (dto.getExpressaoDegeneroId() != null && !dto.getExpressaoDegeneroId().isEmpty()) {
            entity.setExpressaoDegenero(informacoesSensiveisService.getExpressaoDegeneroService().validarId(dto.getExpressaoDegeneroId()));
        }
        if (dto.getPronomePreferidoId() != null && !dto.getPronomePreferidoId().isEmpty()) {
            entity.setPronomePreferido(informacoesSensiveisService.getPronomePreferidoService().validarId(dto.getPronomePreferidoId()));
        }
        if (dto.getCorPeleId() != null && !dto.getCorPeleId().isEmpty()) {
            entity.setCorPele(informacoesSensiveisService.getCorPeleService().validarId(dto.getCorPeleId()));
        }
        if (dto.getNacionalidadeId() != null && !dto.getNacionalidadeId().isEmpty()) {
            entity.setNacionalidade(informacoesSensiveisService.getNacionalidadeService().validarId(dto.getNacionalidadeId()));
        }
        if (dto.getDeficienciaId() != null && !dto.getDeficienciaId().isEmpty()) {
            entity.setDeficiencias(informacoesSensiveisService.getDeficienciasService().validarId(dto.getDeficienciaId()));
        }
        if (dto.getGeracaoId() != null && !dto.getGeracaoId().isEmpty()) {
            entity.setGeracao(informacoesSensiveisService.getGeracaoService().validarId(dto.getGeracaoId()));
        }
        if (dto.getCordaoId() != null && !dto.getCordaoId().isEmpty()) {
            entity.setCordao(informacoesSensiveisService.getCordaoService().validarId(dto.getCordaoId()));
        }
        if (dto.getDoencasCronicasRarasId() != null && !dto.getDoencasCronicasRarasId().isEmpty()) {
            entity.setDoencasCronicasRaras(informacoesSensiveisService.getDoencasCronicasRarasService().validarId(dto.getDoencasCronicasRarasId()));
        }
        if (dto.getNeurodiversidadeId() != null && !dto.getNeurodiversidadeId().isEmpty()) {
            entity.setNeurodiversidade(informacoesSensiveisService.getNeurodiversidadeService().validarId(dto.getNeurodiversidadeId()));
        }

        entity.setPossuiDeficiencia(dto.isPossuiDeficiencia() ? InformacoesSensiveis.SimOuNao.SIM : InformacoesSensiveis.SimOuNao.NAO);
        entity.setFazUsoCordao(dto.isFazUsoCordao() ? InformacoesSensiveis.SimOuNao.SIM : InformacoesSensiveis.SimOuNao.NAO);
        entity.setPossuiDoencaCronicaRara(dto.isPossuiDoencaCronicaRara() ? InformacoesSensiveis.SimOuNao.SIM : InformacoesSensiveis.SimOuNao.NAO);
        entity.setEhNeurodivergente(dto.isEhNeurodivergente() ? InformacoesSensiveis.SimOuNao.SIM : InformacoesSensiveis.SimOuNao.NAO);


        return entity;
    }

    @Override
    protected InformacoesSensiveisResponseDTO toResponseDTO(InformacoesSensiveis entity, UserDetails userDetails) {
        InformacoesSensiveisResponseDTO dto = new InformacoesSensiveisResponseDTO();
        dto.setId(entity.getId());
        dto.setNomeSocial(entity.getNomeSocial());
        dto.setAtivo(entity.isAtivo());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());

        if (entity.getServidor() != null) {
            dto.setServidorId(entity.getServidor().getId());
            dto.setServidorNome(entity.getServidor().getName());
        }
        if (entity.getGenero() != null) {
            dto.setGeneroId(entity.getGenero().getId());
            dto.setGeneroNome(entity.getGenero().getName());
        }
        if (entity.getEstadoCivil() != null) {
            dto.setEstadoCivilId(entity.getEstadoCivil().getId());
            dto.setEstadoCivilNome(entity.getEstadoCivil().getName());
        }
        if (entity.getRacaEtnia() != null) {
            dto.setRacaEtniaId(entity.getRacaEtnia().getId());
            dto.setRacaEtniaNome(entity.getRacaEtnia().getName());
        }
        if (entity.getCorPele() != null) {
            dto.setCorPeleId(entity.getCorPele().getId());
            dto.setCorPeleNome(entity.getCorPele().getName());
        }
        if (entity.getOrientacaoSexual() != null) {
            dto.setOrientacaoSexualId(entity.getOrientacaoSexual().getId());
            dto.setOrientacaoSexualNome(entity.getOrientacaoSexual().getName());
        }
        if (entity.getExpressaoDegenero() != null) {
            dto.setExpressaoDegeneroId(entity.getExpressaoDegenero().getId());
            dto.setExpressaoDegeneroNome(entity.getExpressaoDegenero().getName());
        }
        if (entity.getPronomePreferido() != null) {
            dto.setPronomePreferidoId(entity.getPronomePreferido().getId());
            dto.setPronomePreferidoNome(entity.getPronomePreferido().getName());
        }
        if (entity.getNacionalidade() != null) {
            dto.setNacionalidadeId(entity.getNacionalidade().getId());
            dto.setNacionalidadeNome(entity.getNacionalidade().getName());
        }
        if (entity.getGeracao() != null) {
            dto.setGeracaoId(entity.getGeracao().getId());
            dto.setGeracaoNome(entity.getGeracao().getName());
        }
        if (entity.getCordao() != null) {
            dto.setCordaoId(entity.getCordao().getId());
            dto.setCordaoNome(entity.getCordao().getName());
        }
        if (entity.getDoencasCronicasRaras() != null) {
            dto.setDoencasCronicasRarasId(entity.getDoencasCronicasRaras().getId());
            dto.setDoencasCronicasRarasNome(entity.getDoencasCronicasRaras().getName());
        }
        if (entity.getNeurodiversidade() != null) {
            dto.setNeurodiversidadeId(entity.getNeurodiversidade().getId());
            dto.setNeurodiversidadeNome(entity.getNeurodiversidade().getName());
        }
        if (entity.getDeficiencias() != null) {
            dto.setDeficienciaId(entity.getDeficiencias().getId());
            dto.setDeficienciaNome(entity.getDeficiencias().getName());
        }

        dto.setPossuiDeficiencia(entity.getPossuiDeficiencia() == InformacoesSensiveis.SimOuNao.SIM);
        dto.setFazUsoCordao(entity.getFazUsoCordao() == InformacoesSensiveis.SimOuNao.SIM);
        dto.setPossuiDoencaCronicaRara(entity.getPossuiDoencaCronicaRara() == InformacoesSensiveis.SimOuNao.SIM);
        dto.setEhNeurodivergente(entity.getEhNeurodivergente() == InformacoesSensiveis.SimOuNao.SIM);


        return populateAdminFields(dto, entity, userDetails);
    }


    @Override
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/byServidorId/{servidorId}")
    public ResponseEntity<InformacoesSensiveisResponseDTO> getInformacoesSensiveisByServidorId(
            @PathVariable String servidorId,
            @AuthenticationPrincipal UserDetails userDetails) {
        InformacoesSensiveis informacoesSensiveis = informacoesSensiveisService.findByServidorId(servidorId);

        if (informacoesSensiveis == null) {
            return ResponseEntity.noContent().build();
        }

        InformacoesSensiveisResponseDTO dto = toResponseDTO(informacoesSensiveis, userDetails);
        return ResponseEntity.ok(dto);
    }
}