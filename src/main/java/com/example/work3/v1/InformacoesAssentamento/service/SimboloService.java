package com.example.work3.v1.InformacoesAssentamento.service;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Simbolo;
import com.example.work3.v1.InformacoesAssentamento.repository.SimboloRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SimboloService extends AbstractBaseService<Simbolo, String> {

    @Autowired
    public SimboloService(SimboloRepository repository) {
        super(repository);
    }

    @Override
    public Simbolo findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Simbolo simbolo) {
        simbolo.setAtivo(false);
    }

    @Transactional
    public Simbolo update(String id, Simbolo simbolo) {
        try {
            Simbolo existingSimbolo = validarId(id);
            updateEntityFields(existingSimbolo, simbolo);
            return repository.save(existingSimbolo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Simbolo existingSimbolo, Simbolo newSimbolo) {
        if (newSimbolo.getName() != null) {
            existingSimbolo.setName(newSimbolo.getName());
        }
        if (newSimbolo.isAtivo() != existingSimbolo.isAtivo()) {
            existingSimbolo.setAtivo(newSimbolo.isAtivo());
        }
    }
}
