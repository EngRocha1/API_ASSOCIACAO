package com.associacao.api.v1.Treinamentos.service;

import com.associacao.api.v1.Treinamentos.domain.Treinamentos;
import com.associacao.api.v1.Treinamentos.repository.TreinamentosRepository;
import com.associacao.api.v1.Servidor.Service.ServidorService;
import com.associacao.api.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TreinamentosService {

    private static final Logger logger = LoggerFactory.getLogger(TreinamentosService.class);

    private final TreinamentosRepository repository;
    private final ServidorService servidorService;

    @Autowired
    public TreinamentosService(TreinamentosRepository repository, ServidorService servidorService) {
        this.repository = repository;
        this.servidorService = servidorService;
    }

    public Treinamentos findById(String id) {
        return validarId(id);
    }

    public Treinamentos register(Treinamentos treinamentos) {
        try {
            logger.debug("Iniciando registro do treinamento: {}", treinamentos);
            return repository.save(treinamentos);
        } catch (Exception e) {
            logger.error("Erro ao registrar o treinamento: {}", treinamentos, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    @Transactional
    public Treinamentos update(String id, Treinamentos treinamentos) {
        try {
            logger.debug("Iniciando atualização do treinamento com ID: {}", id);
            Treinamentos existingTreinamentos = validarId(id);
            updateEntityFields(existingTreinamentos, treinamentos);
            return repository.save(existingTreinamentos);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o treinamento com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void delete(String id) {
        try {
            logger.debug("Iniciando exclusão lógica do treinamento com ID: {}", id);
            Treinamentos treinamentos = validarId(id);
            treinamentos.setAtivo(false);
            repository.save(treinamentos);
            logger.debug("Exclusão lógica concluída com sucesso para o treinamento com ID: {}", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir logicamente o treinamento com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Treinamentos existingTreinamentos, Treinamentos newTreinamentos) {
        try {
            logger.debug("Atualizando campos da entidade Treinamentos com ID: {}", existingTreinamentos.getId());

            existingTreinamentos.setTipoAprendizado(newTreinamentos.getTipoAprendizado());
            existingTreinamentos.setCursos(newTreinamentos.getCursos());
            existingTreinamentos.setCpf(newTreinamentos.getCpf());
            existingTreinamentos.setEmail(newTreinamentos.getEmail());
            existingTreinamentos.setTelefone(newTreinamentos.getTelefone());
            existingTreinamentos.setInstituicaoResponsavel(newTreinamentos.getInstituicaoResponsavel());
            existingTreinamentos.setNumeroDocSei(newTreinamentos.getNumeroDocSei());
            existingTreinamentos.setGenero(newTreinamentos.getGenero());
            existingTreinamentos.setDataNascimento(newTreinamentos.getDataNascimento());
            existingTreinamentos.setAtivo(newTreinamentos.isAtivo());

            logger.debug("Campos da entidade Treinamentos atualizados com sucesso para o ID: {}", existingTreinamentos.getId());
        } catch (Exception e) {
            logger.error("Erro ao atualizar campos da entidade Treinamentos com ID: {}", existingTreinamentos.getId(), e);
            throw e;
        }
    }

    public Treinamentos validarId(String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treinamento não encontrado com o ID: " + id));
    }

    public List<Treinamentos> getAll() {
        return repository.findAll();
    }
}