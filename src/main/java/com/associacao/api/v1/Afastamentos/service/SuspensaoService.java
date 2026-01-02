package com.associacao.api.v1.Afastamentos.service;

import com.associacao.api.v1.Afastamentos.domain.Listas.Suspensao;
import com.associacao.api.v1.Afastamentos.repository.SuspensaoRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import com.associacao.api.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SuspensaoService extends AbstractBaseService<Suspensao, String> {

    private static final Logger logger = LoggerFactory.getLogger(SuspensaoService.class);

    @Autowired
    public SuspensaoService(SuspensaoRepository repository) {
        super(repository);
    }

    @Override
    public Suspensao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Suspensao suspensao) {
        suspensao.setAtivo(false);
    }

    @Override
    @Transactional
    public Suspensao update(String id, Suspensao suspensao) {
        try {
            logger.debug("Iniciando atualização da suspensão com ID: {}", id);
            logger.debug("Dados recebidos: {}", suspensao);

            Suspensao existingSuspensao = validarId(id);

            updateEntityFields(existingSuspensao, suspensao);

            logger.debug("Atualização concluída com sucesso para a suspensão com ID: {}", id);
            return repository.save(existingSuspensao);
        } catch (Exception e) {
            logger.error("Erro ao atualizar a suspensão com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }}

    public void updateEntityFields(Suspensao existingSuspensao, Suspensao newSuspensao) {
        try {
            logger.debug("Atualizando campos da entidade Suspensao com ID: {}", existingSuspensao.getId());

            if (newSuspensao.getName() != null) {
                existingSuspensao.setName(newSuspensao.getName());
                logger.debug("Campo 'name' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'name' não fornecido. Mantendo valor existente: {}", existingSuspensao.getName());
            }

            if (newSuspensao.getDescricao() != null) {
                existingSuspensao.setDescricao(newSuspensao.getDescricao());
                logger.debug("Campo 'descricao' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'descricao' não fornecido. Mantendo valor existente: {}", existingSuspensao.getDescricao());
            }

            if (newSuspensao.getAfastamentos().getId() != null) {
                existingSuspensao.getAfastamentos().setId(newSuspensao.getAfastamentos().getId());
                logger.debug("Campo 'afastamentosId' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'afastamentosId' não fornecido. Mantendo valor existente: {}", existingSuspensao.getAfastamentos().getId());
            }

            if (newSuspensao.getAfastamentos().getName() != null) {
                existingSuspensao.getAfastamentos().setName(newSuspensao.getAfastamentos().getName());
                logger.debug("Campo 'afastamentosNome' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'afastamentosNome' não fornecido. Mantendo valor existente: {}", existingSuspensao.getAfastamentos().getName());
            }

            if (newSuspensao.getAfastamentos().getPeriodoInicio() != null) {
                existingSuspensao.getAfastamentos().setPeriodoInicio(newSuspensao.getAfastamentos().getPeriodoInicio());
                logger.debug("Campo 'afastamentosPeriodoInicio' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'afastamentosPeriodoInicio' não fornecido. Mantendo valor existente: {}", existingSuspensao.getAfastamentos().getPeriodoInicio());
            }

            if (newSuspensao.getAfastamentos().getPeriodoFim() != null) {
                existingSuspensao.getAfastamentos().setPeriodoFim(newSuspensao.getAfastamentos().getPeriodoFim());
                logger.debug("Campo 'afastamentosPeriodoFim' atualizado com sucesso.");
            } else {
                logger.debug("Campo 'afastamentosPeriodoFim' não fornecido. Mantendo valor existente: {}", existingSuspensao.getAfastamentos().getPeriodoFim());
            }

            logger.debug("Campos da entidade Suspensao atualizados com sucesso para o ID: {}", existingSuspensao.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("name")) {
                logger.error("Erro ao atualizar o campo 'name' da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            } else if (e.getMessage().contains("descricao")) {
                logger.error("Erro ao atualizar o campo 'descricao' da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            } else if (e.getMessage().contains("afastamentosId")) {
                logger.error("Erro ao atualizar o campo 'afastamentosId' da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            } else if (e.getMessage().contains("afastamentosNome")) {
                logger.error("Erro ao atualizar o campo 'afastamentosNome' da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            } else if (e.getMessage().contains("afastamentosPeriodoInicio")) {
                logger.error("Erro ao atualizar o campo 'afastamentosPeriodoInicio' da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            } else if (e.getMessage().contains("afastamentosPeriodoFim")) {
                logger.error("Erro ao atualizar o campo 'afastamentosPeriodoFim' da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            } else {
                logger.error("Erro ao atualizar campos da entidade Suspensao com ID: {}", existingSuspensao.getId(), e);
            }
            throw e;
        }
    }
}
