package com.example.work3.v1.Treinamentos.service;

import com.example.work3.v1.Treinamentos.domain.Listas.TreinamentoCurso;
import com.example.work3.v1.Treinamentos.repository.TreinamentoCursoRepository;
import com.example.work3.Exceptions.ExceptionHandlerMap;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TreinamentoCursoService {

    private static final Logger logger = LoggerFactory.getLogger(TreinamentoCursoService.class);

    private final TreinamentoCursoRepository repository;

    @Autowired
    public TreinamentoCursoService(TreinamentoCursoRepository repository) {
        this.repository = repository;
    }

    public TreinamentoCurso findById(String id) {
        return validarId(id);
    }

    public TreinamentoCurso register(TreinamentoCurso treinamentoCurso) {
        try {
            logger.debug("Iniciando registro do treinamento curso: {}", treinamentoCurso);
            return repository.save(treinamentoCurso);
        } catch (Exception e) {
            logger.error("Erro ao registrar o treinamento curso: {}", treinamentoCurso, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    @Transactional
    public TreinamentoCurso update(String id, TreinamentoCurso treinamentoCurso) {
        try {
            logger.debug("Iniciando atualização do treinamento curso com ID: {}", id);
            TreinamentoCurso existingTreinamentoCurso = validarId(id);
            updateEntityFields(existingTreinamentoCurso, treinamentoCurso);
            return repository.save(existingTreinamentoCurso);
        } catch (Exception e) {
            logger.error("Erro ao atualizar o treinamento curso com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void delete(String id) {
        try {
            logger.debug("Iniciando exclusão lógica do treinamento curso com ID: {}", id);
            TreinamentoCurso treinamentoCurso = validarId(id);
            treinamentoCurso.setAtivo(false);
            repository.save(treinamentoCurso);
            logger.debug("Exclusão lógica concluída com sucesso para o treinamento curso com ID: {}", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir logicamente o treinamento curso com ID: {}", id, e);
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(TreinamentoCurso existingTreinamentoCurso, TreinamentoCurso newTreinamentoCurso) {
        try {
            logger.debug("Atualizando campos da entidade TreinamentoCurso com ID: {}", existingTreinamentoCurso.getId());

            existingTreinamentoCurso.setName(newTreinamentoCurso.getName());
            existingTreinamentoCurso.setGruposDoCurso(newTreinamentoCurso.getGruposDoCurso());
            existingTreinamentoCurso.setTipoCurso(newTreinamentoCurso.getTipoCurso());
            existingTreinamentoCurso.setTurmaNumero(newTreinamentoCurso.getTurmaNumero());
            existingTreinamentoCurso.setInstrutor(newTreinamentoCurso.getInstrutor());
            existingTreinamentoCurso.setConteudoCurso(newTreinamentoCurso.getConteudoCurso());
            existingTreinamentoCurso.setCargaHoraria(newTreinamentoCurso.getCargaHoraria());
            existingTreinamentoCurso.setAtivo(newTreinamentoCurso.isAtivo());

            logger.debug("Campos da entidade TreinamentoCurso atualizados com sucesso para o ID: {}", existingTreinamentoCurso.getId());
        } catch (Exception e) {
            logger.error("Erro ao atualizar campos da entidade TreinamentoCurso com ID: {}", existingTreinamentoCurso.getId(), e);
            throw e;
        }
    }

    public TreinamentoCurso validarId(String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TreinamentoCurso não encontrado com o ID: " + id));
    }

    public List<TreinamentoCurso> getAll() {
        return repository.findAll();
    }
}