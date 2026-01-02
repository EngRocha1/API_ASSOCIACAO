package com.example.work3.v1.InformacoesSensiveis.Service;

import com.example.work3.v1.InformacoesSensiveis.domain.Listas.CorPele;
import com.example.work3.v1.InformacoesSensiveis.repository.CorPeleRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import com.example.work3.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CorPeleService extends AbstractBaseService<CorPele, String> {

    private static final Logger logger = LoggerFactory.getLogger(CorPeleService.class);

    @Autowired
    public CorPeleService(CorPeleRepository repository) {
        super(repository);
    }

    @Override
    public CorPele findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(CorPele corPele) {
        corPele.setAtivo(false);
    }

    @Override
    @Transactional
    public CorPele update(String id, CorPele corPele) {
        try {
            logger.debug("Iniciando atualização da cor de pele com ID: {}", id);
            logger.debug("Dados recebidos: {}", corPele);

            CorPele existingCorPele = validarId(id);

            updateEntityFields(existingCorPele, corPele);

            logger.debug("Atualização concluída com sucesso para a cor de pele com ID: {}", id);
            return repository.save(existingCorPele);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a cor de pele com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(CorPele existingCorPele, CorPele newCorPele) {
        try {
            logger.debug("Atualizando campos da entidade CorPele com ID: {}", existingCorPele.getId());

            if (newCorPele.getName() != null) {
                existingCorPele.setName(newCorPele.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingCorPele.getName());
            }

            if (newCorPele.getDescricao() != null) {
                existingCorPele.setDescricao(newCorPele.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingCorPele.getDescricao());
            }

            logger.debug("Campos da entidade CorPele atualizados com sucesso para o ID: {}", existingCorPele.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade CorPele com ID: {}", existingCorPele.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade CorPele com ID: {}", existingCorPele.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade CorPele com ID: {}", existingCorPele.getId(), e);
            }
            throw e;
        }
    }
}
