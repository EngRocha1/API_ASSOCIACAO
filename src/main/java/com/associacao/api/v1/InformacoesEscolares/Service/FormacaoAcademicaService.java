package com.associacao.api.v1.InformacoesEscolares.Service;

import com.associacao.api.v1.InformacoesEscolares.domain.Listas.FormacaoAcademica;
import com.associacao.api.v1.InformacoesEscolares.repository.FormacaoAcademicaRepository;
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
public class FormacaoAcademicaService extends AbstractBaseService<FormacaoAcademica, String> {

    private static final Logger logger = LoggerFactory.getLogger(FormacaoAcademicaService.class);

    @Autowired
    public FormacaoAcademicaService(FormacaoAcademicaRepository repository) {
        super(repository);
    }

    @Override
    public FormacaoAcademica findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(FormacaoAcademica formacaoAcademica) {
        formacaoAcademica.setAtivo(false);
    }

    @Override
    @Transactional
    public FormacaoAcademica update(String id, FormacaoAcademica formacaoAcademica) {
        try {
            logger.debug("Iniciando atualização da formação acadêmica com ID: {}", id);
            logger.debug("Dados recebidos: {}", formacaoAcademica);

            FormacaoAcademica existingFormacaoAcademica = validarId(id);

            updateEntityFields(existingFormacaoAcademica, formacaoAcademica);

            logger.debug("Atualização concluída com sucesso para a formação acadêmica com ID: {}", id);
            return repository.save(existingFormacaoAcademica);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a formação acadêmica com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(FormacaoAcademica existingFormacaoAcademica, FormacaoAcademica newFormacaoAcademica) {
        try {
            logger.debug("Atualizando campos da entidade FormacaoAcademica com ID: {}", existingFormacaoAcademica.getId());

            if (newFormacaoAcademica.getName() != null) {
                existingFormacaoAcademica.setName(newFormacaoAcademica.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingFormacaoAcademica.getName());
            }

            if (newFormacaoAcademica.getDescricao() != null) {
                existingFormacaoAcademica.setDescricao(newFormacaoAcademica.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingFormacaoAcademica.getDescricao());
            }

            logger.debug("Campos da entidade FormacaoAcademica atualizados com sucesso para o ID: {}", existingFormacaoAcademica.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade FormacaoAcademica com ID: {}", existingFormacaoAcademica.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade FormacaoAcademica com ID: {}", existingFormacaoAcademica.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade FormacaoAcademica com ID: {}", existingFormacaoAcademica.getId(), e);
            }
            throw e;
        }
    }
}
