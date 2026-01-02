package com.associacao.api.v1.InformacoesEscolares.Service;

import com.associacao.api.v1.InformacoesEscolares.domain.Listas.Curso;
import com.associacao.api.v1.InformacoesEscolares.repository.CursoRepository;
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
public class CursoService extends AbstractBaseService<Curso, String> {

    private static final Logger logger = LoggerFactory.getLogger(CursoService.class);

    @Autowired
    public CursoService(CursoRepository repository) {
        super(repository);
    }

    @Override
    public Curso findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Curso curso) {
        curso.setAtivo(false);
    }

    @Override
    @Transactional
    public Curso update(String id, Curso curso) {
        try {
            logger.debug("Iniciando atualização do curso com ID: {}", id);
            logger.debug("Dados recebidos: {}", curso);

            Curso existingCurso = validarId(id);

            updateEntityFields(existingCurso, curso);

            logger.debug("Atualização concluída com sucesso para o curso com ID: {}", id);
            return repository.save(existingCurso);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o curso com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Curso existingCurso, Curso newCurso) {
        try {
            logger.debug("Atualizando campos da entidade Curso com ID: {}", existingCurso.getId());

            if (newCurso.getName() != null) {
                existingCurso.setName(newCurso.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingCurso.getName());
            }

            if (newCurso.getDescricao() != null) {
                existingCurso.setDescricao(newCurso.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingCurso.getDescricao());
            }

            logger.debug("Campos da entidade Curso atualizados com sucesso para o ID: {}", existingCurso.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Curso com ID: {}", existingCurso.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Curso com ID: {}", existingCurso.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Curso com ID: {}", existingCurso.getId(), e);
            }
            throw e;
        }
    }
}
