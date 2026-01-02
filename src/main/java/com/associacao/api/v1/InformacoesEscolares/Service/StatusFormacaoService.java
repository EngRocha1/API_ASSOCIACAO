package com.associacao.api.v1.InformacoesEscolares.Service;

import com.associacao.api.v1.InformacoesEscolares.domain.Listas.StatusFormacao;
import com.associacao.api.v1.InformacoesEscolares.repository.StatusFormacaoRepository;
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
public class StatusFormacaoService extends AbstractBaseService<StatusFormacao, String> {

    private static final Logger logger = LoggerFactory.getLogger(StatusFormacaoService.class);

    @Autowired
    public StatusFormacaoService(StatusFormacaoRepository repository) {
        super(repository);
    }

    @Override
    public StatusFormacao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(StatusFormacao statusFormacao) {
        statusFormacao.setAtivo(false);
    }

    @Override
    @Transactional
    public StatusFormacao update(String id, StatusFormacao statusFormacao) {
        try {
            logger.debug("Iniciando atualização do status de formação com ID: {}", id);
            logger.debug("Dados recebidos: {}", statusFormacao);

            StatusFormacao existingStatusFormacao = validarId(id);

            updateEntityFields(existingStatusFormacao, statusFormacao);

            logger.debug("Atualização concluída com sucesso para o status de formação com ID: {}", id);
            return repository.save(existingStatusFormacao);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o status de formação com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(StatusFormacao existingStatusFormacao, StatusFormacao newStatusFormacao) {
        try {
            logger.debug("Atualizando campos da entidade StatusFormacao com ID: {}", existingStatusFormacao.getId());

            if (newStatusFormacao.getName() != null) {
                existingStatusFormacao.setName(newStatusFormacao.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingStatusFormacao.getName());
            }

            if (newStatusFormacao.getDescricao() != null) {
                existingStatusFormacao.setDescricao(newStatusFormacao.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingStatusFormacao.getDescricao());
            }

            logger.debug("Campos da entidade StatusFormacao atualizados com sucesso para o ID: {}", existingStatusFormacao.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade StatusFormacao com ID: {}", existingStatusFormacao.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade StatusFormacao com ID: {}", existingStatusFormacao.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade StatusFormacao com ID: {}", existingStatusFormacao.getId(), e);
            }
            throw e;
        }
    }
}
