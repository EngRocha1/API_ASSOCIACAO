package com.example.work3.v1.InformacoesSensiveis.Service;

import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Genero;
import com.example.work3.v1.InformacoesSensiveis.repository.GeneroRepository;
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
public class GeneroService extends AbstractBaseService<Genero, String> {

    private static final Logger logger = LoggerFactory.getLogger(GeneroService.class);

    @Autowired
    public GeneroService(GeneroRepository repository) {
        super(repository);
    }

    @Override
    public Genero findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(Genero genero) {
        genero.setAtivo(false);
    }

    @Override
    @Transactional
    public Genero update(String id, Genero genero) {
        try {
            logger.debug("Iniciando atualização do gênero com ID: {}", id);
            logger.debug("Dados recebidos: {}", genero);

            Genero existingGenero = validarId(id);

            updateEntityFields(existingGenero, genero);

            logger.debug("Atualização concluída com sucesso para o gênero com ID: {}", id);
            return repository.save(existingGenero);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o gênero com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Genero existingGenero, Genero newGenero) {
        try {
            logger.debug("Atualizando campos da entidade Genero com ID: {}", existingGenero.getId());

            if (newGenero.getName() != null) {
                existingGenero.setName(newGenero.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingGenero.getName());
            }

            if (newGenero.getDescricao() != null) {
                existingGenero.setDescricao(newGenero.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingGenero.getDescricao());
            }

            logger.debug("Campos da entidade Genero atualizados com sucesso para o ID: {}", existingGenero.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Genero com ID: {}", existingGenero.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Genero com ID: {}", existingGenero.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Genero com ID: {}", existingGenero.getId(), e);
            }
            throw e;
        }
    }
}
