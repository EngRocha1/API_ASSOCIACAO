package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.ExpressaoDegenero;
import com.associacao.api.v1.InformacoesSensiveis.repository.ExpressaoDegeneroRepository;
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
public class ExpressaoDegeneroService extends AbstractBaseService<ExpressaoDegenero, String> {

    private static final Logger logger = LoggerFactory.getLogger(ExpressaoDegeneroService.class);

    @Autowired
    public ExpressaoDegeneroService(ExpressaoDegeneroRepository repository) {
        super(repository);
    }

    @Override
    public ExpressaoDegenero findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(ExpressaoDegenero expressaoDegenero) {
        expressaoDegenero.setAtivo(false);
    }

    @Override
    @Transactional
    public ExpressaoDegenero update(String id, ExpressaoDegenero expressaoDegenero) {
        try {
            logger.debug("Iniciando atualização da expressão de gênero com ID: {}", id);
            logger.debug("Dados recebidos: {}", expressaoDegenero);

            ExpressaoDegenero existingExpressaoDegenero = validarId(id);

            updateEntityFields(existingExpressaoDegenero, expressaoDegenero);

            logger.debug("Atualização concluída com sucesso para a expressão de gênero com ID: {}", id);
            return repository.save(existingExpressaoDegenero);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a expressão de gênero com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(ExpressaoDegenero existingExpressaoDegenero, ExpressaoDegenero newExpressaoDegenero) {
        try {
            logger.debug("Atualizando campos da entidade ExpressaoDegenero com ID: {}", existingExpressaoDegenero.getId());

            if (newExpressaoDegenero.getName() != null) {
                existingExpressaoDegenero.setName(newExpressaoDegenero.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingExpressaoDegenero.getName());
            }

            if (newExpressaoDegenero.getDescricao() != null) {
                existingExpressaoDegenero.setDescricao(newExpressaoDegenero.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingExpressaoDegenero.getDescricao());
            }

            logger.debug("Campos da entidade ExpressaoDegenero atualizados com sucesso para o ID: {}", existingExpressaoDegenero.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade ExpressaoDegenero com ID: {}", existingExpressaoDegenero.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade ExpressaoDegenero com ID: {}", existingExpressaoDegenero.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade ExpressaoDegenero com ID: {}", existingExpressaoDegenero.getId(), e);
            }
            throw e;
        }
    }
}
