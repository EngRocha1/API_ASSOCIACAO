package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.OrientacaoSexual;
import com.associacao.api.v1.InformacoesSensiveis.repository.OrientacaoSexualRepository;
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
public class OrientacaoSexualService extends AbstractBaseService<OrientacaoSexual, String> {

    private static final Logger logger = LoggerFactory.getLogger(OrientacaoSexualService.class);

    @Autowired
    public OrientacaoSexualService(OrientacaoSexualRepository repository) {
        super(repository);
    }

    @Override
    public OrientacaoSexual findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(OrientacaoSexual orientacaoSexual) {
        orientacaoSexual.setAtivo(false);
    }

    @Override
    @Transactional
    public OrientacaoSexual update(String id, OrientacaoSexual orientacaoSexual) {
        try {
            logger.debug("Iniciando atualização da orientação sexual com ID: {}", id);
            logger.debug("Dados recebidos: {}", orientacaoSexual);

            OrientacaoSexual existingOrientacaoSexual = validarId(id);

            updateEntityFields(existingOrientacaoSexual, orientacaoSexual);

            logger.debug("Atualização concluída com sucesso para a orientação sexual com ID: {}", id);
            return repository.save(existingOrientacaoSexual);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a orientação sexual com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(OrientacaoSexual existingOrientacaoSexual, OrientacaoSexual newOrientacaoSexual) {
        try {
            logger.debug("Atualizando campos da entidade OrientacaoSexual com ID: {}", existingOrientacaoSexual.getId());

            if (newOrientacaoSexual.getName() != null) {
                existingOrientacaoSexual.setName(newOrientacaoSexual.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingOrientacaoSexual.getName());
            }

            if (newOrientacaoSexual.getDescricao() != null) {
                existingOrientacaoSexual.setDescricao(newOrientacaoSexual.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingOrientacaoSexual.getDescricao());
            }

            logger.debug("Campos da entidade OrientacaoSexual atualizados com sucesso para o ID: {}", existingOrientacaoSexual.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade OrientacaoSexual com ID: {}", existingOrientacaoSexual.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade OrientacaoSexual com ID: {}", existingOrientacaoSexual.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade OrientacaoSexual com ID: {}", existingOrientacaoSexual.getId(), e);
            }
            throw e;
        }
    }
}
