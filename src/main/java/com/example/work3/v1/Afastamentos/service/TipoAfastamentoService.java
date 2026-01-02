package com.example.work3.v1.Afastamentos.service;

import com.example.work3.v1.Afastamentos.domain.Listas.TipoAfastamento;
import com.example.work3.v1.Afastamentos.repository.TipoAfastamentoRepository;
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
public class TipoAfastamentoService extends AbstractBaseService<TipoAfastamento, String> {

    private static final Logger logger = LoggerFactory.getLogger(TipoAfastamentoService.class);

    @Autowired
    public TipoAfastamentoService(TipoAfastamentoRepository repository) {
        super(repository);
    }

    @Override
    public TipoAfastamento findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(TipoAfastamento tipoAfastamento) {
        tipoAfastamento.setAtivo(false);
    }

    @Override
    @Transactional
    public TipoAfastamento update(String id, TipoAfastamento tipoAfastamento) {
        try {
            logger.debug("Iniciando atualização do tipo de afastamento com ID: {}", id);
            logger.debug("Dados recebidos: {}", tipoAfastamento);

            TipoAfastamento existingTipoAfastamento = validarId(id);

            updateEntityFields(existingTipoAfastamento, tipoAfastamento);

            logger.debug("Atualização concluída com sucesso para o tipo de afastamento com ID: {}", id);
            return repository.save(existingTipoAfastamento);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o tipo de afastamento com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(TipoAfastamento existingTipoAfastamento, TipoAfastamento newTipoAfastamento) {
        try {
            logger.debug("Atualizando campos da entidade TipoAfastamento com ID: {}", existingTipoAfastamento.getId());

            if (newTipoAfastamento.getName() != null) {
                existingTipoAfastamento.setName(newTipoAfastamento.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingTipoAfastamento.getName());
            }

            if (newTipoAfastamento.getDescricao() != null) {
                existingTipoAfastamento.setDescricao(newTipoAfastamento.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingTipoAfastamento.getDescricao());
            }

            logger.debug("Campos da entidade TipoAfastamento atualizados com sucesso para o ID: {}", existingTipoAfastamento.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade TipoAfastamento com ID: {}", existingTipoAfastamento.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade TipoAfastamento com ID: {}", existingTipoAfastamento.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade TipoAfastamento com ID: {}", existingTipoAfastamento.getId(), e);
            }
            throw e;
        }
    }
}
