package com.example.work3.v1.Afastamentos.service;

import com.example.work3.v1.Afastamentos.domain.Listas.FluxoAprovacao;
import com.example.work3.v1.Afastamentos.repository.FluxoAprovacaoRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import com.example.work3.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FluxoAprovacaoService extends AbstractBaseService<FluxoAprovacao, String> {

    private static final Logger logger = LoggerFactory.getLogger(FluxoAprovacaoService.class);

    @Autowired
    public FluxoAprovacaoService(FluxoAprovacaoRepository repository) {
        super(repository);
    }

    @Override
    public FluxoAprovacao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(FluxoAprovacao fluxoAprovacao) {
        fluxoAprovacao.setAtivo(false);
    }

    @Override
    @Transactional
    public FluxoAprovacao update(String id, FluxoAprovacao fluxoAprovacao) {
        try {
            logger.debug("Iniciando atualização do fluxo de aprovação com ID: {}", id);
            logger.debug("Dados recebidos: {}", fluxoAprovacao);

            FluxoAprovacao existingFluxoAprovacao = validarId(id);

            updateEntityFields(existingFluxoAprovacao, fluxoAprovacao);

            logger.debug("Atualização concluída com sucesso para o fluxo de aprovação com ID: {}", id);
            return repository.save(existingFluxoAprovacao);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o fluxo de aprovação com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(FluxoAprovacao existingFluxoAprovacao, FluxoAprovacao newFluxoAprovacao) {
        try {
            logger.debug("Atualizando campos da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId());

            if (newFluxoAprovacao.getName() != null) {
                existingFluxoAprovacao.setName(newFluxoAprovacao.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getName());
            }

            if (newFluxoAprovacao.getDescricao() != null) {
                existingFluxoAprovacao.setDescricao(newFluxoAprovacao.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getDescricao());
            }

            if (newFluxoAprovacao.getAprovacaoTipo() != null) {
                existingFluxoAprovacao.setAprovacaoTipo(newFluxoAprovacao.getAprovacaoTipo());
                logger.debug("Campo 'AprovacaoTipo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'AprovacaoTipo' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getAprovacaoTipo());
            }

            if (newFluxoAprovacao.getDataAprovacao() != null) {
                existingFluxoAprovacao.setDataAprovacao(newFluxoAprovacao.getDataAprovacao());
                logger.debug("Campo 'DataAprovacao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'DataAprovacao' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getDataAprovacao());
            }

            if (newFluxoAprovacao.getDataSolicitacao() != null) {
                existingFluxoAprovacao.setDataSolicitacao(newFluxoAprovacao.getDataSolicitacao());
                logger.debug("Campo 'DataSolicitacao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'DataSolicitacao' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getDataSolicitacao());
            }

            if (newFluxoAprovacao.getAprovadoPor() != null) {
                existingFluxoAprovacao.setAprovadoPor(newFluxoAprovacao.getAprovadoPor());
                logger.debug("Campo 'AprovadoPor' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'AprovadoPor' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getAprovadoPor());
            }

            if (newFluxoAprovacao.getSolicitadoPor() != null) {
                existingFluxoAprovacao.setSolicitadoPor(newFluxoAprovacao.getSolicitadoPor());
                logger.debug("Campo 'SolicitadoPor' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'SolicitadoPor' não fornecido. Mantendo valor existente: {}", existingFluxoAprovacao.getSolicitadoPor());
            }

            logger.debug("Campos da entidade FluxoAprovacao atualizados com sucesso para o ID: {}", existingFluxoAprovacao.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else if (e.getMessage().contains("AprovacaoTipo")) {
                logger.error("Erro ao atualizar o campo 'AprovacaoTipo' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else if (e.getMessage().contains("DataAprovacao")) {
                logger.error("Erro ao atualizar o campo 'DataAprovacao' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else if (e.getMessage().contains("DataSolicitacao")) {
                logger.error("Erro ao atualizar o campo 'DataSolicitacao' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else if (e.getMessage().contains("AprovadoPor")) {
                logger.error("Erro ao atualizar o campo 'AprovadoPor' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else if (e.getMessage().contains("SolicitadoPor")) {
                logger.error("Erro ao atualizar o campo 'SolicitadoPor' da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade FluxoAprovacao com ID: {}", existingFluxoAprovacao.getId(), e);
            }
            throw e;
        }
    }
}