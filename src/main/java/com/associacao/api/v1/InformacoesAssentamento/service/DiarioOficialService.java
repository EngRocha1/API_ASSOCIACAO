package com.associacao.api.v1.InformacoesAssentamento.service;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.DiarioOficial;
import com.associacao.api.v1.InformacoesAssentamento.repository.DiarioOficialRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DiarioOficialService extends AbstractBaseService<DiarioOficial, String> {

    @Autowired
    public DiarioOficialService(DiarioOficialRepository repository) {
        super(repository);
    }

    @Override
    public DiarioOficial findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(DiarioOficial diarioOficial) {
        diarioOficial.setAtivo(false);
    }

    @Transactional
    public DiarioOficial update(String id, DiarioOficial diarioOficial) {
        try {
            DiarioOficial existingDiarioOficial = validarId(id);
            updateEntityFields(existingDiarioOficial, diarioOficial);
            return repository.save(existingDiarioOficial);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }
    public void updateEntityFields(DiarioOficial existingDiarioOficial, DiarioOficial newDiarioOficial) {
        if (newDiarioOficial.getName() != null) {
            existingDiarioOficial.setName(newDiarioOficial.getName());
        }
        if (newDiarioOficial.getTipo() != null) {
            existingDiarioOficial.setTipo(newDiarioOficial.getTipo());
        }
        if (newDiarioOficial.isAtivo() != existingDiarioOficial.isAtivo()) {
            existingDiarioOficial.setAtivo(newDiarioOficial.isAtivo());
        }
        if (newDiarioOficial.getSimbolo() != null) {
            if (newDiarioOficial.getSimbolo().getId() != null) {
                existingDiarioOficial.getSimbolo().setId(newDiarioOficial.getSimbolo().getId());
            }
            if (newDiarioOficial.getSimbolo().getName() != null) {
                existingDiarioOficial.getSimbolo().setName(newDiarioOficial.getSimbolo().getName());
            }
            if (newDiarioOficial.getSimbolo().getDescricao() != null) {
                existingDiarioOficial.getSimbolo().setDescricao(newDiarioOficial.getSimbolo().getDescricao());
            }
        }
        if (newDiarioOficial.getVinculo() != null) {
            if (newDiarioOficial.getVinculo().getId() != null) {
                existingDiarioOficial.getVinculo().setId(newDiarioOficial.getVinculo().getId());
            }
            if (newDiarioOficial.getVinculo().getName() != null) {
                existingDiarioOficial.getVinculo().setName(newDiarioOficial.getVinculo().getName());
            }
            if (newDiarioOficial.getVinculo().getDescricao() != null) {
                existingDiarioOficial.getVinculo().setDescricao(newDiarioOficial.getVinculo().getDescricao());
            }
        }
        if (newDiarioOficial.getCargo() != null) {
            if (newDiarioOficial.getCargo().getId() != null) {
                existingDiarioOficial.getCargo().setId(newDiarioOficial.getCargo().getId());
            }
            if (newDiarioOficial.getCargo().getName() != null) {
                existingDiarioOficial.getCargo().setName(newDiarioOficial.getCargo().getName());
            }
            if (newDiarioOficial.getCargo().getDescricao() != null) {
                existingDiarioOficial.getCargo().setDescricao(newDiarioOficial.getCargo().getDescricao());
            }
        }
        if (newDiarioOficial.getDataPublicacao() != null) {
            existingDiarioOficial.setDataPublicacao(newDiarioOficial.getDataPublicacao());
        }
        if (newDiarioOficial.getDataEfeito() != null) {
            existingDiarioOficial.setDataEfeito(newDiarioOficial.getDataEfeito());
        }
    }

}

