package com.associacao.api.v1.InformacoesEscolares.Service;

import com.associacao.api.v1.InformacoesEscolares.domain.Listas.NivelEnsino;
import com.associacao.api.v1.InformacoesEscolares.repository.NivelEnsinoRepository;
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
public class NivelEnsinoService extends AbstractBaseService<NivelEnsino, String> {

    private static final Logger logger = LoggerFactory.getLogger(NivelEnsinoService.class);

    @Autowired
    public NivelEnsinoService(NivelEnsinoRepository repository) {
        super(repository);
    }

    @Override
    public NivelEnsino findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(NivelEnsino nivelEnsino) {
        nivelEnsino.setAtivo(false);
    }

    @Override
    @Transactional
    public NivelEnsino update(String id, NivelEnsino nivelEnsino) {
        try {
            logger.debug("Iniciando atualização do nível de ensino com ID: {}", id);
            logger.debug("Dados recebidos: {}", nivelEnsino);

            NivelEnsino existingNivelEnsino = validarId(id);

            updateEntityFields(existingNivelEnsino, nivelEnsino);

            logger.debug("Atualização concluída com sucesso para o nível de ensino com ID: {}", id);
            return repository.save(existingNivelEnsino);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o nível de ensino com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(NivelEnsino existingNivelEnsino, NivelEnsino newNivelEnsino) {
        try {
            logger.debug("Atualizando campos da entidade NivelEnsino com ID: {}", existingNivelEnsino.getId());

            if (newNivelEnsino.getName() != null) {
                existingNivelEnsino.setName(newNivelEnsino.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingNivelEnsino.getName());
            }

            if (newNivelEnsino.getDescricao() != null) {
                existingNivelEnsino.setDescricao(newNivelEnsino.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingNivelEnsino.getDescricao());
            }

            logger.debug("Campos da entidade NivelEnsino atualizados com sucesso para o ID: {}", existingNivelEnsino.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade NivelEnsino com ID: {}", existingNivelEnsino.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade NivelEnsino com ID: {}", existingNivelEnsino.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade NivelEnsino com ID: {}", existingNivelEnsino.getId(), e);
            }
            throw e;
        }
    }
}
