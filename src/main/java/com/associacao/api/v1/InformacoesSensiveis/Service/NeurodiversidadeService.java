package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.Exceptions.ExceptionHandlerMap;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.Neurodiversidade;
import com.associacao.api.v1.InformacoesSensiveis.repository.NeurodiversidadeRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NeurodiversidadeService extends AbstractBaseService<Neurodiversidade, String> {

    private static final Logger logger = LoggerFactory.getLogger(NeurodiversidadeService.class);

    @Autowired
    public NeurodiversidadeService(NeurodiversidadeRepository repository) {
        super(repository);
    }

    @Override
    public Neurodiversidade findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Neurodiversidade neurodiversidade) {
        neurodiversidade.setAtivo(false);
    }

    @Override
    @Transactional
    public Neurodiversidade update(String id, Neurodiversidade neurodiversidade) {
        try {
            logger.debug("Iniciando atualização de Neurodiversidade com ID: {}", id);
            logger.debug("Dados recebidos: {}", neurodiversidade);

            Neurodiversidade existingNeurodiversidade = validarId(id);

            updateEntityFields(existingNeurodiversidade, neurodiversidade);

            logger.debug("Atualização concluída com sucesso para Neurodiversidade com ID: {}", id);
            return repository.save(existingNeurodiversidade);
        } catch (Exception e) {
            logger.error("Erro ao atualizar Neurodiversidade com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Neurodiversidade existingNeurodiversidade, Neurodiversidade newNeurodiversidade) {
        try {
            logger.debug("Atualizando campos da entidade Neurodiversidade com ID: {}", existingNeurodiversidade.getId());

            if (newNeurodiversidade.getName() != null) {
                existingNeurodiversidade.setName(newNeurodiversidade.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingNeurodiversidade.getName());
            }

            if (newNeurodiversidade.getDescricao() != null) {
                existingNeurodiversidade.setDescricao(newNeurodiversidade.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingNeurodiversidade.getDescricao());
            }

            logger.debug("Campos da entidade Neurodiversidade atualizados com sucesso para o ID: {}", existingNeurodiversidade.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Neurodiversidade com ID: {}", existingNeurodiversidade.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Neurodiversidade com ID: {}", existingNeurodiversidade.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Neurodiversidade com ID: {}", existingNeurodiversidade.getId(), e);
            }
            throw e;
        }
    }
}