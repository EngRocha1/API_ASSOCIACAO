package com.associacao.api.v1.Treinamentos.service;

import com.associacao.api.v1.Treinamentos.domain.Listas.GrupoDoCurso;
import com.associacao.api.v1.Treinamentos.repository.GrupoDoCursoRepository;
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
public class GrupoDoCursoService extends AbstractBaseService<GrupoDoCurso, String> {

    private static final Logger logger = LoggerFactory.getLogger(GrupoDoCursoService.class);

    @Autowired
    public GrupoDoCursoService(GrupoDoCursoRepository repository) {
        super(repository);
    }

    @Override
    public GrupoDoCurso findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(GrupoDoCurso grupoDoCurso) {
        grupoDoCurso.setAtivo(false);
    }

    @Override
    @Transactional
    public GrupoDoCurso update(String id, GrupoDoCurso grupoDoCurso) {
        try {
            logger.debug("Iniciando atualização do grupo do curso com ID: {}", id);
            logger.debug("Dados recebidos: {}", grupoDoCurso);

            GrupoDoCurso existingGrupoDoCurso = validarId(id);

            updateEntityFields(existingGrupoDoCurso, grupoDoCurso);

            logger.debug("Atualização concluída com sucesso para o grupo do curso com ID: {}", id);
            return repository.save(existingGrupoDoCurso);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o grupo do curso com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(GrupoDoCurso existingGrupoDoCurso, GrupoDoCurso newGrupoDoCurso) {
        try {
            logger.debug("Atualizando campos da entidade GrupoDoCurso com ID: {}", existingGrupoDoCurso.getId());

            if (newGrupoDoCurso.getName() != null) {
                existingGrupoDoCurso.setName(newGrupoDoCurso.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingGrupoDoCurso.getName());
            }

            logger.debug("Campos da entidade GrupoDoCurso atualizados com sucesso para o ID: {}", existingGrupoDoCurso.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade GrupoDoCurso com ID: {}", existingGrupoDoCurso.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade GrupoDoCurso com ID: {}", existingGrupoDoCurso.getId(), e);
            }
            throw e;
        }
    }
}