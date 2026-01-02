package com.associacao.api.v1.InformacoesEscolares.Service;

import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Periodo;
import com.associacao.api.v1.InformacoesEscolares.repository.PeriodoRepository;
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
public class PeriodoService extends AbstractBaseService<Periodo, String> {

    private static final Logger logger = LoggerFactory.getLogger(PeriodoService.class);

    @Autowired
    public PeriodoService(PeriodoRepository repository) {
        super(repository);
    }

    @Override
    public Periodo findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Periodo periodo) {
        periodo.setAtivo(false);
    }

    @Override
    @Transactional
    public Periodo update(String id, Periodo periodo) {
        try {
            logger.debug("Iniciando atualização do período com ID: {}", id);
            logger.debug("Dados recebidos: {}", periodo);

            Periodo existingPeriodo = validarId(id);

            updateEntityFields(existingPeriodo, periodo);

            logger.debug("Atualização concluída com sucesso para o período com ID: {}", id);
            return repository.save(existingPeriodo);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o período com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Periodo existingPeriodo, Periodo newPeriodo) {
        try {
            logger.debug("Atualizando campos da entidade Periodo com ID: {}", existingPeriodo.getId());

            if (newPeriodo.getName() != null) {
                existingPeriodo.setName(newPeriodo.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingPeriodo.getName());
            }

            if (newPeriodo.getDescricao() != null) {
                existingPeriodo.setDescricao(newPeriodo.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingPeriodo.getDescricao());
            }

            logger.debug("Campos da entidade Periodo atualizados com sucesso para o ID: {}", existingPeriodo.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Periodo com ID: {}", existingPeriodo.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Periodo com ID: {}", existingPeriodo.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Periodo com ID: {}", existingPeriodo.getId(), e);
            }
            throw e;
        }
    }
}
