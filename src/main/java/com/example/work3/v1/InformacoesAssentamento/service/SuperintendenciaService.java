package com.example.work3.v1.InformacoesAssentamento.service;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Superintendencia;
import com.example.work3.v1.InformacoesAssentamento.repository.SuperintendenciaRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SuperintendenciaService extends AbstractBaseService<Superintendencia, String> {

    @Autowired
    public SuperintendenciaService(SuperintendenciaRepository repository) {
        super(repository);
    }

    @Override
    public Superintendencia findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Superintendencia superintendencia) {
        superintendencia.setAtivo(false);
    }

    @Transactional
    public Superintendencia update(String id, Superintendencia superintendencia) {
        try {
            Superintendencia existingSuperintendencia = validarId(id);
            updateEntityFields(existingSuperintendencia, superintendencia);
            return repository.save(existingSuperintendencia);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Superintendencia existingSuperintendencia, Superintendencia newSuperintendencia) {
        if (newSuperintendencia.getName() != null) {
            existingSuperintendencia.setName(newSuperintendencia.getName());
        }
        if (newSuperintendencia.isAtivo() != existingSuperintendencia.isAtivo()) {
            existingSuperintendencia.setAtivo(newSuperintendencia.isAtivo());
        }
    }
}
