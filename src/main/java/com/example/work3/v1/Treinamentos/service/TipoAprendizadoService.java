package com.example.work3.v1.Treinamentos.service;

import com.example.work3.v1.Treinamentos.domain.Listas.TipoAprendizado;
import com.example.work3.v1.Treinamentos.repository.TipoAprendizadoRepository;
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
public class TipoAprendizadoService extends AbstractBaseService<TipoAprendizado, String> {

    private static final Logger logger = LoggerFactory.getLogger(TipoAprendizadoService.class);

    @Autowired
    public TipoAprendizadoService(TipoAprendizadoRepository repository) {
        super(repository);
    }

    @Override
    public TipoAprendizado findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(TipoAprendizado tipoAprendizado) {
        tipoAprendizado.setAtivo(false);
    }

    @Override
    @Transactional
    public TipoAprendizado update(String id, TipoAprendizado tipoAprendizado) {
        try {
            logger.debug("Iniciando atualização do tipo de aprendizado com ID: {}", id);
            logger.debug("Dados recebidos: {}", tipoAprendizado);

            TipoAprendizado existingTipoAprendizado = validarId(id);

            updateEntityFields(existingTipoAprendizado, tipoAprendizado);

            logger.debug("Atualização concluída com sucesso para o tipo de aprendizado com ID: {}", id);
            return repository.save(existingTipoAprendizado);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o tipo de aprendizado com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(TipoAprendizado existingTipoAprendizado, TipoAprendizado newTipoAprendizado) {
        try {
            logger.debug("Atualizando campos da entidade TipoAprendizado com ID: {}", existingTipoAprendizado.getId());

            if (newTipoAprendizado.getName() != null) {
                existingTipoAprendizado.setName(newTipoAprendizado.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingTipoAprendizado.getName());
            }

            if (newTipoAprendizado.getDescricao() != null) {
                existingTipoAprendizado.setDescricao(newTipoAprendizado.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingTipoAprendizado.getDescricao());
            }

            logger.debug("Campos da entidade TipoAprendizado atualizados com sucesso para o ID: {}", existingTipoAprendizado.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade TipoAprendizado com ID: {}", existingTipoAprendizado.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade TipoAprendizado com ID: {}", existingTipoAprendizado.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade TipoAprendizado com ID: {}", existingTipoAprendizado.getId(), e);
            }
            throw e;
        }
    }
}
