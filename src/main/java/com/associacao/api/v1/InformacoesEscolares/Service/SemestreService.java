package com.associacao.api.v1.InformacoesEscolares.Service;

import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Semestre;
import com.associacao.api.v1.InformacoesEscolares.repository.SemestreRepository;
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
public class SemestreService extends AbstractBaseService<Semestre, String> {

    private static final Logger logger = LoggerFactory.getLogger(SemestreService.class);

    @Autowired
    public SemestreService(SemestreRepository repository) {
        super(repository);
    }

    @Override
    public Semestre findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Semestre semestre) {
        semestre.setAtivo(false);
    }

    @Override
    @Transactional
    public Semestre update(String id, Semestre semestre) {
        try {
            logger.debug("Iniciando atualização do semestre com ID: {}", id);
            logger.debug("Dados recebidos: {}", semestre);

            Semestre existingSemestre = validarId(id);

            updateEntityFields(existingSemestre, semestre);

            logger.debug("Atualização concluída com sucesso para o semestre com ID: {}", id);
            return repository.save(existingSemestre);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o semestre com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Semestre existingSemestre, Semestre newSemestre) {
        try {
            logger.debug("Atualizando campos da entidade Semestre com ID: {}", existingSemestre.getId());

            if (newSemestre.getName() != null) {
                existingSemestre.setName(newSemestre.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingSemestre.getName());
            }

            if (newSemestre.getDescricao() != null) {
                existingSemestre.setDescricao(newSemestre.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingSemestre.getDescricao());
            }

            logger.debug("Campos da entidade Semestre atualizados com sucesso para o ID: {}", existingSemestre.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Semestre com ID: {}", existingSemestre.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Semestre com ID: {}", existingSemestre.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Semestre com ID: {}", existingSemestre.getId(), e);
            }
            throw e;
        }
    }
}
