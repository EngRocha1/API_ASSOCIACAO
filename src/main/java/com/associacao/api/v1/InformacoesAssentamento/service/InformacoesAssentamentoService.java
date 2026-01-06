package com.associacao.api.v1.InformacoesAssentamento.service;

import com.associacao.api.v1.InformacoesAssentamento.domain.InformacoesAssentamento;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.*;
import com.associacao.api.v1.InformacoesAssentamento.repository.InformacoesAssentamentoRepository;
import com.associacao.api.v1.Servidor.Service.ServidorService;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InformacoesAssentamentoService
        extends AbstractBaseService<InformacoesAssentamento, String> {

    private static final Logger logger = LoggerFactory.getLogger(InformacoesAssentamentoService.class);
    private final InformacoesAssentamentoRepository informacoesAssentamentoRepository;

    @Getter
    private final ServidorService servidorService;
    @Getter
    private final AtoMovimentacaoService atoMovimentacaoService;
    @Getter
    private final DiretoriaService diretoriaService;
    @Getter
    private final SuperintendenciaService superintendenciaService;
    @Getter
    private final VinculoService vinculoService;
    @Getter
    private final LotacaoService lotacaoService;
    @Getter
    private final OrgaoGovService orgaoGovService;

    @Autowired
    public InformacoesAssentamentoService(
            InformacoesAssentamentoRepository informacoesAssentamentoRepository,
            ServidorService servidorService,
            AtoMovimentacaoService atoMovimentacaoService,
            DiretoriaService diretoriaService,
            SuperintendenciaService superintendenciaService,
            VinculoService vinculoService,
            LotacaoService lotacaoService,
            OrgaoGovService orgaoGovService
    ) {
        super(informacoesAssentamentoRepository);
        this.informacoesAssentamentoRepository = informacoesAssentamentoRepository;
        this.servidorService = servidorService;
        this.atoMovimentacaoService = atoMovimentacaoService;
        this.diretoriaService = diretoriaService;
        this.superintendenciaService = superintendenciaService;
        this.vinculoService = vinculoService;
        this.lotacaoService = lotacaoService;
        this.orgaoGovService = orgaoGovService;
    }

    private void initializeLazyRelations(InformacoesAssentamento entity) {
        if (entity == null) return;
        logger.debug("Inicializando relacionamentos LAZY para InformacoesAssentamento com ID: {}", entity.getId());
        if (entity.getServidor() != null) Hibernate.initialize(entity.getServidor());
        if (entity.getAtomovimentacao() != null) Hibernate.initialize(entity.getAtomovimentacao());
        if (entity.getDiretoria() != null) Hibernate.initialize(entity.getDiretoria());
        if (entity.getOrgaogov() != null) Hibernate.initialize(entity.getOrgaogov());
        if (entity.getLotacao() != null) Hibernate.initialize(entity.getLotacao());
        if (entity.getSuperintendencia() != null) Hibernate.initialize(entity.getSuperintendencia());

        logger.debug("Relacionamentos LAZY inicializados.");
    }

    @Transactional(readOnly = true)
    public InformacoesAssentamento findByServidorId(String servidorId) {
        logger.debug("Tentando encontrar informações assentamento para o ID do servidor: {}", servidorId);
        Optional<InformacoesAssentamento> entityOptional = informacoesAssentamentoRepository.findByServidorId(servidorId);

        if (entityOptional.isEmpty()) {
            logger.info("Nenhuma informação assentamento encontrada para o ID do Servidor: {}. Retornando null.", servidorId);
            return null;
        }

        InformacoesAssentamento entity = entityOptional.get();
        initializeLazyRelations(entity);
        return entity;
    }

    @Override
    public InformacoesAssentamento findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(InformacoesAssentamento informacoesAssentamento) {
        informacoesAssentamento.setAtivo(false);
    }

    @Transactional
    public InformacoesAssentamento update(String id, InformacoesAssentamento informacoesAssentamento) {
        try {
            InformacoesAssentamento existingInformacoesAssentamento = validarId(id); // Garante que o assentamento existe e está ativo
            logger.debug("InformacoesAssentamento existente (antes da atualização): {}", existingInformacoesAssentamento);
            logger.debug("Novas informações assentamento (do DTO): {}", informacoesAssentamento);

            updateEntityFields(existingInformacoesAssentamento, informacoesAssentamento);

            InformacoesAssentamento updatedEntity = repository.save(existingInformacoesAssentamento);
            logger.debug("InformacoesAssentamento atualizado e salvo: {}", updatedEntity);
            return updatedEntity;

        } catch (ResponseStatusException e) {
            logger.error("Erro de status ao atualizar InformacoesAssentamento com ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar InformacoesAssentamento com ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    @Transactional
    public void updateEntityFields(InformacoesAssentamento existingInformacoesAssentamento, InformacoesAssentamento newInformacoesAssentamento) {

        if (newInformacoesAssentamento.getMatriculaInstitucional() != null) {
            existingInformacoesAssentamento.setMatriculaInstitucional(newInformacoesAssentamento.getMatriculaInstitucional());
        }
        if (newInformacoesAssentamento.getEmailInstitucionalGov() != null) {
            existingInformacoesAssentamento.setEmailInstitucionalGov(newInformacoesAssentamento.getEmailInstitucionalGov());
        }

        if (newInformacoesAssentamento.isAtivo() != existingInformacoesAssentamento.isAtivo()) {
            existingInformacoesAssentamento.setAtivo(newInformacoesAssentamento.isAtivo());
        }

        // Servidor
        if (newInformacoesAssentamento.getServidor() != null && newInformacoesAssentamento.getServidor().getId() != null) {
            Servidor servidor = servidorService.validarId(newInformacoesAssentamento.getServidor().getId());
            existingInformacoesAssentamento.setServidor(servidor);
        }

        // Lotação
        if (newInformacoesAssentamento.getLotacao() != null && newInformacoesAssentamento.getLotacao().getId() != null) {
            Lotacao lotacao = lotacaoService.validarId(newInformacoesAssentamento.getLotacao().getId());
            existingInformacoesAssentamento.setLotacao(lotacao);
        } else if (newInformacoesAssentamento.getLotacao() != null && newInformacoesAssentamento.getLotacao().getId() == null) {
            // Se o ID for explicitamente null (frontend enviou um ID vazio para desvincular)
            existingInformacoesAssentamento.setLotacao(null);
        }

        // Órgão Governamental
        if (newInformacoesAssentamento.getOrgaogov() != null && newInformacoesAssentamento.getOrgaogov().getId() != null) {
            OrgaoGov orgaogov = orgaoGovService.validarId(newInformacoesAssentamento.getOrgaogov().getId());
            existingInformacoesAssentamento.setOrgaogov(orgaogov);
        } else if (newInformacoesAssentamento.getOrgaogov() != null && newInformacoesAssentamento.getOrgaogov().getId() == null) {
            existingInformacoesAssentamento.setOrgaogov(null);
        }

        // Superintendência
        if (newInformacoesAssentamento.getSuperintendencia() != null && newInformacoesAssentamento.getSuperintendencia().getId() != null) {
            Superintendencia superintendencia = superintendenciaService.validarId(newInformacoesAssentamento.getSuperintendencia().getId());
            existingInformacoesAssentamento.setSuperintendencia(superintendencia);
        } else if (newInformacoesAssentamento.getSuperintendencia() != null && newInformacoesAssentamento.getSuperintendencia().getId() == null) {
            existingInformacoesAssentamento.setSuperintendencia(null);
        }

        // Diretoria
        if (newInformacoesAssentamento.getDiretoria() != null && newInformacoesAssentamento.getDiretoria().getId() != null) {
            Diretoria diretoria = diretoriaService.validarId(newInformacoesAssentamento.getDiretoria().getId());
            existingInformacoesAssentamento.setDiretoria(diretoria);
        } else if (newInformacoesAssentamento.getDiretoria() != null && newInformacoesAssentamento.getDiretoria().getId() == null) {
            existingInformacoesAssentamento.setDiretoria(null);
        }

        // Ato Movimentacao
        if (newInformacoesAssentamento.getAtomovimentacao() != null && newInformacoesAssentamento.getAtomovimentacao().getId() != null) {
            AtoMovimentacao atoMovimentacao = atoMovimentacaoService.validarId(newInformacoesAssentamento.getAtomovimentacao().getId());
            existingInformacoesAssentamento.setAtomovimentacao(atoMovimentacao);
        } else if (newInformacoesAssentamento.getAtomovimentacao() != null && newInformacoesAssentamento.getAtomovimentacao().getId() == null) {
            existingInformacoesAssentamento.setAtomovimentacao(null);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        InformacoesAssentamento informacoesAssentamento = validarId(id);
        try {
            setAtivoFalse(informacoesAssentamento);
            informacoesAssentamentoRepository.save(informacoesAssentamento);
            logger.debug("Informações Assentamento com ID {} marcadas como inativas com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao marcar as informações assentamento com ID: {} como inativas", id, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao marcar as informações assentamento como inativas.", e);
        }
    }
}