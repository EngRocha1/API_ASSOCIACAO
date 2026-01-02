package com.associacao.api.v1.InformacoesSensiveis.Service;

import com.associacao.api.Exceptions.ExceptionHandlerMap;
import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.DoencasCronicasRaras;
import com.associacao.api.v1.InformacoesSensiveis.repository.DoencasCronicasRarasRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DoencasCronicasRarasService extends AbstractBaseService<DoencasCronicasRaras, String> {

    private static final Logger logger = LoggerFactory.getLogger(DoencasCronicasRarasService.class);

    @Autowired
    public DoencasCronicasRarasService(DoencasCronicasRarasRepository repository) {
        super(repository);
    }

    @Override
    public DoencasCronicasRaras findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(DoencasCronicasRaras doencasCronicasRaras) {
        doencasCronicasRaras.setAtivo(false);
    }

    @Override
    @Transactional
    public DoencasCronicasRaras update(String id, DoencasCronicasRaras doencasCronicasRaras) {
        try {
            logger.debug("Iniciando atualização de Doença Crônica/Rara com ID: {}", id);
            logger.debug("Dados recebidos: {}", doencasCronicasRaras);

            DoencasCronicasRaras existingDoenca = validarId(id);

            updateEntityFields(existingDoenca, doencasCronicasRaras);

            logger.debug("Atualização concluída com sucesso para Doença Crônica/Rara com ID: {}", id);
            return repository.save(existingDoenca);
        } catch (Exception e) {
            logger.error("Erro ao atualizar Doença Crônica/Rara com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(DoencasCronicasRaras existingDoenca, DoencasCronicasRaras newDoenca) {
        try {
            logger.debug("Atualizando campos da entidade Doença Crônica/Rara com ID: {}", existingDoenca.getId());

            if (newDoenca.getName() != null) {
                existingDoenca.setName(newDoenca.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingDoenca.getName());
            }

            if (newDoenca.getDescricao() != null) {
                existingDoenca.setDescricao(newDoenca.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingDoenca.getDescricao());
            }

            logger.debug("Campos da entidade Doença Crônica/Rara atualizados com sucesso para o ID: {}", existingDoenca.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Doença Crônica/Rara com ID: {}", existingDoenca.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Doença Crônica/Rara com ID: {}", existingDoenca.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Doença Crônica/Rara com ID: {}", existingDoenca.getId(), e);
            }
            throw e;
        }
    }
}