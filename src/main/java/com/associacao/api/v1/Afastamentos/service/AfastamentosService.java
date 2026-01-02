package com.associacao.api.v1.Afastamentos.service;

import com.associacao.api.v1.Afastamentos.domain.Afastamentos;
import com.associacao.api.v1.Afastamentos.repository.AfastamentosRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import com.associacao.api.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AfastamentosService extends AbstractBaseService<Afastamentos, String> {

    private static final Logger logger = LoggerFactory.getLogger(AfastamentosService.class);

    @Autowired
    public AfastamentosService(AfastamentosRepository repository) {
        super(repository);
    }

    @Override
    public Afastamentos findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Afastamentos afastamentos) {
        afastamentos.setAtivo(false);
    }

    @Override
    @Transactional
    public Afastamentos update(String id, Afastamentos afastamentos) {
        try {
            logger.debug("Iniciando atualização do afastamento com ID: {}", id);
            logger.debug("Dados recebidos: {}", afastamentos);

            Afastamentos existingAfastamentos = validarId(id);

            updateEntityFields(existingAfastamentos, afastamentos);

            logger.debug("Atualização concluída com sucesso para o afastamento com ID: {}", id);
            return repository.save(existingAfastamentos);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o afastamento com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Afastamentos existingAfastamentos, Afastamentos newAfastamentos) {
        try {
            logger.debug("Atualizando campos da entidade Afastamentos com ID: {}", existingAfastamentos.getId());

            if (newAfastamentos.getName() != null) {
                existingAfastamentos.setName(newAfastamentos.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getName());
            }

            if (newAfastamentos.getDescricao() != null) {
                existingAfastamentos.setDescricao(newAfastamentos.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getDescricao());
            }

            if (newAfastamentos.getDataReferencia() != null) {
                existingAfastamentos.setDataReferencia(newAfastamentos.getDataReferencia());
                logger.debug("Campo 'DataReferencia' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'DataReferencia' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getDataReferencia());
            }

            if (newAfastamentos.getPeriodoInicio() != null) {
                existingAfastamentos.setPeriodoInicio(newAfastamentos.getPeriodoInicio());
                logger.debug("Campo 'PeriodoInicio' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'PeriodoInicio' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getPeriodoInicio());
            }

            if (newAfastamentos.getPeriodoFim() != null) {
                existingAfastamentos.setPeriodoFim(newAfastamentos.getPeriodoFim());
                logger.debug("Campo 'PeriodoFim' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'PeriodoFim' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getPeriodoFim());
            }

            if (newAfastamentos.getStatusTipo() != null) {
                existingAfastamentos.setStatusTipo(newAfastamentos.getStatusTipo());
                logger.debug("Campo 'statusTipo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'statusTipo' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getStatusTipo());
            }

            if (newAfastamentos.getSolicitacaoTipo() != null) {
                existingAfastamentos.setSolicitacaoTipo(newAfastamentos.getSolicitacaoTipo());
                logger.debug("Campo 'solicitacaoTipo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'solicitacaoTipo' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getSolicitacaoTipo());
            }

            if (newAfastamentos.getRequerimentoTipo() != null) {
                existingAfastamentos.setRequerimentoTipo(newAfastamentos.getRequerimentoTipo());
                logger.debug("Campo 'requerimentoTipo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'requerimentoTipo' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getRequerimentoTipo());
            }

            if (newAfastamentos.getSiapiTipo() != null) {
                existingAfastamentos.setSiapiTipo(newAfastamentos.getSiapiTipo());
                logger.debug("Campo 'sIAPITipo' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'sIAPITipo' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getSiapiTipo());
            }

            if (newAfastamentos.getJustificativa() != null) {
                existingAfastamentos.setJustificativa(newAfastamentos.getJustificativa());
                logger.debug("Campo 'justificativa' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'justificativa' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getJustificativa());
            }

            if (newAfastamentos.getNumeroSEI() != null) {
                existingAfastamentos.setNumeroSEI(newAfastamentos.getNumeroSEI());
                logger.debug("Campo 'numeroSEI' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'numeroSEI' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getNumeroSEI());
            }

            if (newAfastamentos.getServidor().getId() != null) {
                existingAfastamentos.getServidor().setId(newAfastamentos.getServidor().getId());
                logger.debug("Campo 'servidorId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'servidorId' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getServidor().getId());
            }

            if (newAfastamentos.getServidor().getName() != null) {
                existingAfastamentos.getServidor().setName(newAfastamentos.getServidor().getName());
                logger.debug("Campo 'servidorNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'servidorNome' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getServidor().getName());
            }

            if (newAfastamentos.getCid() != null) {
                existingAfastamentos.setCid(newAfastamentos.getCid());
                logger.debug("Campo 'cid' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'cid' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getCid());
            }

            if (newAfastamentos.getFluxo().getId() != null) {
                existingAfastamentos.getFluxo().setId(newAfastamentos.getFluxo().getId());
                logger.debug("Campo 'fluxoAprovacao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'fluxoAprovacao' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getFluxo().getId());
            }

            if (newAfastamentos.getFluxo().getName() != null) {
                existingAfastamentos.getFluxo().setName(newAfastamentos.getFluxo().getName());
                logger.debug("Campo 'fluxoAprovacaoNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'fluxoAprovacaoNome' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getFluxo().getName());
            }

            if (newAfastamentos.getSuspensao() != null) {
                existingAfastamentos.getSuspensao().setId(newAfastamentos.getSuspensao().getId());
                logger.debug("Campo 'suspensao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'suspensao' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getSuspensao().getId());
            }

            if (newAfastamentos.getSuspensao().getName() != null) {
                existingAfastamentos.getSuspensao().setName(newAfastamentos.getSuspensao().getName());
                logger.debug("Campo 'suspensaoNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'suspensaoNome' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getSuspensao().getName());
            }

            if (newAfastamentos.getTipoafastamento().getId() != null) {
                existingAfastamentos.getTipoafastamento().setId(newAfastamentos.getTipoafastamento().getId());
                logger.debug("Campo 'tipoAfastamento' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'tipoAfastamento' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getTipoafastamento().getId());
            }

            if (newAfastamentos.getTipoafastamento().getName() != null) {
                existingAfastamentos.getTipoafastamento().setName(newAfastamentos.getTipoafastamento().getName());
                logger.debug("Campo 'tipoAfastamentoNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'tipoAfastamentoNome' não fornecido. Mantendo valor existente: {}", existingAfastamentos.getTipoafastamento().getName());
            }


            logger.debug("Campos da entidade Afastamentos atualizados com sucesso para o ID: {}", existingAfastamentos.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("DataReferencia")) {
                logger.error("Erro ao atualizar o campo 'DataReferencia' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("PeriodoInicio")) {
                logger.error("Erro ao atualizar o campo 'PeriodoInicio' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("PeriodoFim")) {
                logger.error("Erro ao atualizar o campo 'PeriodoFim' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("statusTipo")) {
                logger.error("Erro ao atualizar o campo 'statusTipo' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("solicitacaoTipo")) {
                logger.error("Erro ao atualizar o campo 'solicitacaoTipo' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("requerimentoTipo")) {
                logger.error("Erro ao atualizar o campo 'requerimentoTipo' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("sIAPITipo")) {
                logger.error("Erro ao atualizar o campo 'sIAPITipo' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("justificativa")) {
                logger.error("Erro ao atualizar o campo 'justificativa' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("numeroSEI")) {
                logger.error("Erro ao atualizar o campo 'numeroSEI' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("servidorId")) {
                logger.error("Erro ao atualizar o campo 'servidorId' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("servidorNome")) {
                logger.error("Erro ao atualizar o campo 'servidorNome' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("cid")) {
                logger.error("Erro ao atualizar o campo 'cid' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("fluxoAprovacao")) {
                logger.error("Erro ao atualizar o campo 'fluxoAprovacao' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("suspensao")) {
                logger.error("Erro ao atualizar o campo 'suspensao' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else if (e.getMessage().contains("tipoAfastamento")) {
                logger.error("Erro ao atualizar o campo 'tipoAfastamento' da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Afastamentos com ID: {}", existingAfastamentos.getId(), e);
            }
            throw e;
        }
    }

}