package com.example.work3.v1.InformacoesSensiveis.Service;

import com.example.work3.Exceptions.ExceptionHandlerMap;
import com.example.work3.v1.InformacoesSensiveis.domain.Listas.Deficiencias;
import com.example.work3.v1.InformacoesSensiveis.repository.DeficienciasRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DeficienciasService extends AbstractBaseService<Deficiencias, String> {

    private static final Logger logger = LoggerFactory.getLogger(DeficienciasService.class);

    @Autowired
    public DeficienciasService(DeficienciasRepository repository) {
        super(repository);
    }

    @Override
    public Deficiencias findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Deficiencias deficiencias) {
        deficiencias.setAtivo(false);
    }

    @Override
    @Transactional
    public Deficiencias update(String id, Deficiencias deficiencias) {
        try {
            logger.debug("Iniciando atualização da Deficiencias com ID: {}", id);
            logger.debug("Dados recebidos: {}", deficiencias);

            Deficiencias existingDeficiencias = validarId(id);

            updateEntityFields(existingDeficiencias, deficiencias);

            logger.debug("Atualização concluída com sucesso para a Deficiencias com ID: {}", id);
            return repository.save(existingDeficiencias);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a Deficiencias com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Deficiencias existingDeficiencias, Deficiencias newDeficiencias) {
        try {
            logger.debug("Atualizando campos da entidade Deficiencias com ID: {}", existingDeficiencias.getId());

            if (newDeficiencias.getName() != null) {
                existingDeficiencias.setName(newDeficiencias.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingDeficiencias.getName());
            }

            if (newDeficiencias.getDescricao() != null) {
                existingDeficiencias.setDescricao(newDeficiencias.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingDeficiencias.getDescricao());
            }

            logger.debug("Campos da entidade Deficiencias atualizados com sucesso para o ID: {}", existingDeficiencias.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Deficiencias com ID: {}", existingDeficiencias.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Deficiencias com ID: {}", existingDeficiencias.getId(), e);
            } else if (e.getMessage().contains("outraDeficiencia")) {
                logger.error("Erro ao atualizar o campo 'outraDeficiencia' da entidade Deficiencias com ID: {}", existingDeficiencias.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Deficiencias com ID: {}", existingDeficiencias.getId(), e);
            }
            throw e;
        }
    }
}
