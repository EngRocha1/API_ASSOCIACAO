package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.RacaEtnia;
import com.associacao.api.v1.InformacoesSensiveis.repository.RacaEtniaRepository;
import com.associacao.api.Exceptions.ExceptionHandlerMap;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RacaEtniaService extends AbstractBaseService<RacaEtnia, String> {

    private static final Logger logger = LoggerFactory.getLogger(RacaEtniaService.class);

    @Autowired
    public RacaEtniaService(RacaEtniaRepository repository) {
        super(repository);
    }

    @Override
    public RacaEtnia findById(String id) {
        return validarId(id);
    }

    @Override
    protected void setAtivoFalse(RacaEtnia racaEtnia) {
        racaEtnia.setAtivo(false);
    }

    @Override
    @Transactional
    public RacaEtnia update(String id, RacaEtnia racaEtnia) {
        try {
            logger.debug("Iniciando atualização da raça/etnia com ID: {}", id);
            logger.debug("Dados recebidos: {}", racaEtnia);

            RacaEtnia existingRacaEtnia = validarId(id);

            updateEntityFields(existingRacaEtnia, racaEtnia);

            logger.debug("Atualização concluída com sucesso para a raça/etnia com ID: {}", id);
            return repository.save(existingRacaEtnia);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a raça/etnia com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(RacaEtnia existingRacaEtnia, RacaEtnia newRacaEtnia) {
        try {
            logger.debug("Atualizando campos da entidade RacaEtnia com ID: {}", existingRacaEtnia.getId());

            if (newRacaEtnia.getName() != null) {
                existingRacaEtnia.setName(newRacaEtnia.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingRacaEtnia.getName());
            }

            if (newRacaEtnia.getDescricao() != null) {
                existingRacaEtnia.setDescricao(newRacaEtnia.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingRacaEtnia.getDescricao());
            }

            logger.debug("Campos da entidade RacaEtnia atualizados com sucesso para o ID: {}", existingRacaEtnia.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade RacaEtnia com ID: {}", existingRacaEtnia.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade RacaEtnia com ID: {}", existingRacaEtnia.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade RacaEtnia com ID: {}", existingRacaEtnia.getId(), e);
            }
            throw e;
        }
    }
}
