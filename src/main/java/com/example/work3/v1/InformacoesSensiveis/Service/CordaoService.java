package com.example.work3.v1.InformacoesSensiveis.Service;

import com.example.work3.Exceptions.ExceptionHandlerMap;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Cordao;
import com.example.work3.v1.InformacoesSensiveis.repository.CordaoRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CordaoService extends AbstractBaseService<Cordao, String> {

    private static final Logger logger = LoggerFactory.getLogger(CordaoService.class);

    @Autowired
    public CordaoService(CordaoRepository repository) {
        super(repository);
    }

    @Override
    public Cordao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Cordao cordao) {
        cordao.setAtivo(false);
    }

    @Override
    @Transactional
    public Cordao update(String id, Cordao cordao) {
        try {
            logger.debug("Iniciando atualização do Cordao com ID: {}", id);
            logger.debug("Dados recebidos: {}", cordao);

            Cordao existingCordao = validarId(id);

            updateEntityFields(existingCordao, cordao);

            logger.debug("Atualização concluída com sucesso para o Cordao com ID: {}", id);
            return repository.save(existingCordao);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o Cordao com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Cordao existingCordao, Cordao newCordao) {
        try {
            logger.debug("Atualizando campos da entidade Cordao com ID: {}", existingCordao.getId());

            if (newCordao.getName() != null) {
                existingCordao.setName(newCordao.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingCordao.getName());
            }

            if (newCordao.getDescricao() != null) {
                existingCordao.setDescricao(newCordao.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingCordao.getDescricao());
            }

            logger.debug("Campos da entidade Cordao atualizados com sucesso para o ID: {}", existingCordao.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Cordao com ID: {}", existingCordao.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Cordao com ID: {}", existingCordao.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Cordao com ID: {}", existingCordao.getId(), e);
            }
            throw e;
        }
    }
}
