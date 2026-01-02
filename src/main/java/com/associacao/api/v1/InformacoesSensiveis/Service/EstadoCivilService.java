package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.EstadoCivil;
import com.associacao.api.v1.InformacoesSensiveis.repository.EstadoCivilRepository;
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
public class EstadoCivilService extends AbstractBaseService<EstadoCivil, String> {

    private static final Logger logger = LoggerFactory.getLogger(EstadoCivilService.class);

    @Autowired
    public EstadoCivilService(EstadoCivilRepository repository) {
        super(repository);
    }

    @Override
    public EstadoCivil findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(EstadoCivil estadoCivil) {
        estadoCivil.setAtivo(false);
    }

    @Override
    @Transactional
    public EstadoCivil update(String id, EstadoCivil estadoCivil) {
        try {
            logger.debug("Iniciando atualização do estado civil com ID: {}", id);
            logger.debug("Dados recebidos: {}", estadoCivil);

            EstadoCivil existingEstadoCivil = validarId(id);

            updateEntityFields(existingEstadoCivil, estadoCivil);

            logger.debug("Atualização concluída com sucesso para o estado civil com ID: {}", id);
            return repository.save(existingEstadoCivil);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o estado civil com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(EstadoCivil existingEstadoCivil, EstadoCivil newEstadoCivil) {
        try {
            logger.debug("Atualizando campos da entidade EstadoCivil com ID: {}", existingEstadoCivil.getId());

            if (newEstadoCivil.getName() != null) {
                existingEstadoCivil.setName(newEstadoCivil.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingEstadoCivil.getName());
            }

            if (newEstadoCivil.getDescricao() != null) {
                existingEstadoCivil.setDescricao(newEstadoCivil.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingEstadoCivil.getDescricao());
            }

            logger.debug("Campos da entidade EstadoCivil atualizados com sucesso para o ID: {}", existingEstadoCivil.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade EstadoCivil com ID: {}", existingEstadoCivil.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade EstadoCivil com ID: {}", existingEstadoCivil.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade EstadoCivil com ID: {}", existingEstadoCivil.getId(), e);
            }
            throw e;
        }
    }
}

