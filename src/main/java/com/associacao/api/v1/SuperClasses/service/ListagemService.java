package com.associacao.api.v1.SuperClasses.service;

import com.associacao.api.Exceptions.ExceptionHandlerMap;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ListagemService extends AbstractBaseService<Listagem, String> {

    private static final Logger logger = LoggerFactory.getLogger(ListagemService.class);

    @Autowired
    public ListagemService(ListagemRepository repository) {
        super(repository);
    }

    @Override
    public Listagem findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(Listagem listagem) {
        listagem.setAtivo(false);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Listagem listagem = validarId(id);
        try {
            setAtivoFalse(listagem);
            repository.save(listagem);
            logger.debug("Listagem com ID {} marcado como inativo com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao marcar o Listagem com ID: {} como inativo", id, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao marcar o Listagem como inativo.", e);
        }
    }

    @Override
    @Transactional
    public Listagem update(String id, Listagem listagem) {
        try {
            logger.debug("Iniciando atualização da listagem com ID: {}", id);
            logger.debug("Dados recebidos: {}", listagem);

            Listagem existingListagem = validarId(id);

            updateEntityFields(existingListagem, listagem);

            logger.debug("Atualização concluída com sucesso para a listagem com ID: {}", id);
            return repository.save(existingListagem);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a listagem com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Listagem existingListagem, Listagem newListagem) {
        try {
            logger.debug("Atualizando campos da entidade Listagem com ID: {}", existingListagem.getId());

            if (newListagem.getName() != null) {
                existingListagem.setName(newListagem.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingListagem.getName());
            }

            if (newListagem.getDescricao() != null) {
                existingListagem.setDescricao(newListagem.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingListagem.getDescricao());
            }

            logger.debug("Campos da entidade Listagem atualizados com sucesso para o ID: {}", existingListagem.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Listagem com ID: {}", existingListagem.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Listagem com ID: {}", existingListagem.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Listagem com ID: {}", existingListagem.getId(), e);
            }
            throw e;
        }
    }
}
