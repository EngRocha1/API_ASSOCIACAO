package com.associacao.api.v1.Treinamentos.service;

import com.associacao.api.v1.Treinamentos.domain.Listas.TipoTreinamentos;
import com.associacao.api.v1.Treinamentos.repository.TipoTreinamentosRepository;
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
public class TipoTreinamentosService extends AbstractBaseService<TipoTreinamentos, String> {

    private static final Logger logger = LoggerFactory.getLogger(TipoTreinamentosService.class);

    @Autowired
    public TipoTreinamentosService(TipoTreinamentosRepository repository) {
        super(repository);
    }

    @Override
    public TipoTreinamentos findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(TipoTreinamentos tipoTreinamentos) {
        tipoTreinamentos.setAtivo(false);
    }

    @Override
    @Transactional
    public TipoTreinamentos update(String id, TipoTreinamentos tipoTreinamentos) {
        try {
            logger.debug("Iniciando atualização do tipo de aprendizado com ID: {}", id);
            logger.debug("Dados recebidos: {}", tipoTreinamentos);

            TipoTreinamentos existingTipoTreinamentos = validarId(id);

            updateEntityFields(existingTipoTreinamentos, tipoTreinamentos);

            logger.debug("Atualização concluída com sucesso para o tipo de aprendizado com ID: {}", id);
            return repository.save(existingTipoTreinamentos);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o tipo de aprendizado com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(TipoTreinamentos existingTipoTreinamentos, TipoTreinamentos newTipoTreinamentos) {
        try {
            logger.debug("Atualizando campos da entidade TipoTreinamentos com ID: {}", existingTipoTreinamentos.getId());

            if (newTipoTreinamentos.getName() != null) {
                existingTipoTreinamentos.setName(newTipoTreinamentos.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingTipoTreinamentos.getName());
            }

            if (newTipoTreinamentos.getDescricao() != null) {
                existingTipoTreinamentos.setDescricao(newTipoTreinamentos.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingTipoTreinamentos.getDescricao());
            }

            logger.debug("Campos da entidade TipoTreinamentos atualizados com sucesso para o ID: {}", existingTipoTreinamentos.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade TipoTreinamentos com ID: {}", existingTipoTreinamentos.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade TipoTreinamentos com ID: {}", existingTipoTreinamentos.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade TipoTreinamentos com ID: {}", existingTipoTreinamentos.getId(), e);
            }
            throw e;
        }
    }
}
