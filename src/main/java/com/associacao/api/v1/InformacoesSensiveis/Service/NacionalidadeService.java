package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.Exceptions.ExceptionHandlerMap;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Nacionalidade;
import com.associacao.api.v1.InformacoesSensiveis.repository.NacionalidadeRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NacionalidadeService extends AbstractBaseService<Nacionalidade, String> {

    private static final Logger logger = LoggerFactory.getLogger(NacionalidadeService.class);

    @Autowired
    public NacionalidadeService(NacionalidadeRepository repository) {
        super(repository);
    }

    @Override
    public Nacionalidade findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Nacionalidade nacionalidade) {
        nacionalidade.setAtivo(false);
    }

    @Override
    @Transactional
    public Nacionalidade update(String id, Nacionalidade nacionalidade) {
        try {
            logger.debug("Iniciando atualização de Nacionalidade com ID: {}", id);
            logger.debug("Dados recebidos: {}", nacionalidade);

            Nacionalidade existingNacionalidade = validarId(id);

            updateEntityFields(existingNacionalidade, nacionalidade);

            logger.debug("Atualização concluída com sucesso para Nacionalidade com ID: {}", id);
            return repository.save(existingNacionalidade);
        } catch (Exception e) {
            logger.error("Erro ao atualizar Nacionalidade com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Nacionalidade existingNacionalidade, Nacionalidade newNacionalidade) {
        try {
            logger.debug("Atualizando campos da entidade Nacionalidade com ID: {}", existingNacionalidade.getId());

            if (newNacionalidade.getName() != null) {
                existingNacionalidade.setName(newNacionalidade.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingNacionalidade.getName());
            }

            if (newNacionalidade.getDescricao() != null) {
                existingNacionalidade.setDescricao(newNacionalidade.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingNacionalidade.getDescricao());
            }

            logger.debug("Campos da entidade Nacionalidade atualizados com sucesso para o ID: {}", existingNacionalidade.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Nacionalidade com ID: {}", existingNacionalidade.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Nacionalidade com ID: {}", existingNacionalidade.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Nacionalidade com ID: {}", existingNacionalidade.getId(), e);
            }
            throw e;
        }
    }
}