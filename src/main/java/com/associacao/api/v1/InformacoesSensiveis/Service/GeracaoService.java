package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.Exceptions.ExceptionHandlerMap;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Geracao;
import com.associacao.api.v1.InformacoesSensiveis.repository.GeracaoRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GeracaoService extends AbstractBaseService<Geracao, String> {

    private static final Logger logger = LoggerFactory.getLogger(GeracaoService.class);

    @Autowired
    public GeracaoService(GeracaoRepository repository) {
        super(repository);
    }

    @Override
    public Geracao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Geracao geracao) {
        geracao.setAtivo(false);
    }

    @Override
    @Transactional
    public Geracao update(String id, Geracao geracao) {
        try {
            logger.debug("Iniciando atualização da Geração com ID: {}", id);
            logger.debug("Dados recebidos: {}", geracao);

            Geracao existingGeracao = validarId(id);

            updateEntityFields(existingGeracao, geracao);

            logger.debug("Atualização concluída com sucesso para a Geração com ID: {}", id);
            return repository.save(existingGeracao);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a Geração com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Geracao existingGeracao, Geracao newGeracao) {
        try {
            logger.debug("Atualizando campos da entidade Geração com ID: {}", existingGeracao.getId());

            if (newGeracao.getName() != null) {
                existingGeracao.setName(newGeracao.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingGeracao.getName());
            }

            if (newGeracao.getDescricao() != null) {
                existingGeracao.setDescricao(newGeracao.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingGeracao.getDescricao());
            }

            logger.debug("Campos da entidade Geração atualizados com sucesso para o ID: {}", existingGeracao.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Geração com ID: {}", existingGeracao.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Geração com ID: {}", existingGeracao.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Geração com ID: {}", existingGeracao.getId(), e);
            }
            throw e;
        }
    }
}