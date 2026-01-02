package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.PronomePreferido;
import com.associacao.api.v1.InformacoesSensiveis.repository.PronomePreferidoRepository;
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
public class PronomePreferidoService extends AbstractBaseService<PronomePreferido, String> {

    private static final Logger logger = LoggerFactory.getLogger(PronomePreferidoService.class);

    @Autowired
    public PronomePreferidoService(PronomePreferidoRepository repository) {
        super(repository);
    }

    @Override
    public PronomePreferido findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(PronomePreferido pronomePreferido) {
        pronomePreferido.setAtivo(false);
    }

    @Override
    @Transactional
    public PronomePreferido update(String id, PronomePreferido pronomePreferido) {
        try {
            logger.debug("Iniciando atualização do pronome preferido com ID: {}", id);
            logger.debug("Dados recebidos: {}", pronomePreferido);

            PronomePreferido existingPronomePreferido = validarId(id);

            updateEntityFields(existingPronomePreferido, pronomePreferido);

            logger.debug("Atualização concluída com sucesso para o pronome preferido com ID: {}", id);
            return repository.save(existingPronomePreferido);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o pronome preferido com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(PronomePreferido existingPronomePreferido, PronomePreferido newPronomePreferido) {
        try {
            logger.debug("Atualizando campos da entidade PronomePreferido com ID: {}", existingPronomePreferido.getId());

            if (newPronomePreferido.getName() != null) {
                existingPronomePreferido.setName(newPronomePreferido.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingPronomePreferido.getName());
            }

            if (newPronomePreferido.getDescricao() != null) {
                existingPronomePreferido.setDescricao(newPronomePreferido.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingPronomePreferido.getDescricao());
            }

            logger.debug("Campos da entidade PronomePreferido atualizados com sucesso para o ID: {}", existingPronomePreferido.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade PronomePreferido com ID: {}", existingPronomePreferido.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade PronomePreferido com ID: {}", existingPronomePreferido.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade PronomePreferido com ID: {}", existingPronomePreferido.getId(), e);
            }
            throw e;
        }
    }
}
