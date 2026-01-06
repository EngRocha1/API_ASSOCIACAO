package com.associacao.api.v1.InformacoesAssentamento.service;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.AtoMovimentacao;
import com.associacao.api.v1.InformacoesAssentamento.repository.AtoMovimentacaoRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AtoMovimentacaoService extends AbstractBaseService<AtoMovimentacao, String> {

    @Autowired
    public AtoMovimentacaoService(AtoMovimentacaoRepository repository) {
        super(repository);
    }

    @Override
    public AtoMovimentacao findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(AtoMovimentacao atoMovimentacao) {
        atoMovimentacao.setAtivo(false);
    }

    @Transactional
    public AtoMovimentacao update(String id, AtoMovimentacao atoMovimentacao) {
        try {
            AtoMovimentacao existingAtoMovimentacao = validarId(id);
            updateEntityFields(existingAtoMovimentacao, atoMovimentacao);
            return repository.save(existingAtoMovimentacao);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }
    public void updateEntityFields(AtoMovimentacao existingAtoMovimentacao, AtoMovimentacao newAtoMovimentacao) {
        if (newAtoMovimentacao.getName() != null) {
            existingAtoMovimentacao.setName(newAtoMovimentacao.getName());
        }
        if (newAtoMovimentacao.getTipo() != null) {
            existingAtoMovimentacao.setTipo(newAtoMovimentacao.getTipo());
        }
        if (newAtoMovimentacao.isAtivo() != existingAtoMovimentacao.isAtivo()) {
            existingAtoMovimentacao.setAtivo(newAtoMovimentacao.isAtivo());
        }
        if (newAtoMovimentacao.getSimbolo() != null) {
            if (newAtoMovimentacao.getSimbolo().getId() != null) {
                existingAtoMovimentacao.getSimbolo().setId(newAtoMovimentacao.getSimbolo().getId());
            }
            if (newAtoMovimentacao.getSimbolo().getName() != null) {
                existingAtoMovimentacao.getSimbolo().setName(newAtoMovimentacao.getSimbolo().getName());
            }
            if (newAtoMovimentacao.getSimbolo().getDescricao() != null) {
                existingAtoMovimentacao.getSimbolo().setDescricao(newAtoMovimentacao.getSimbolo().getDescricao());
            }
        }
        if (newAtoMovimentacao.getVinculo() != null) {
            if (newAtoMovimentacao.getVinculo().getId() != null) {
                existingAtoMovimentacao.getVinculo().setId(newAtoMovimentacao.getVinculo().getId());
            }
            if (newAtoMovimentacao.getVinculo().getName() != null) {
                existingAtoMovimentacao.getVinculo().setName(newAtoMovimentacao.getVinculo().getName());
            }
            if (newAtoMovimentacao.getVinculo().getDescricao() != null) {
                existingAtoMovimentacao.getVinculo().setDescricao(newAtoMovimentacao.getVinculo().getDescricao());
            }
        }
        if (newAtoMovimentacao.getCargo() != null) {
            if (newAtoMovimentacao.getCargo().getId() != null) {
                existingAtoMovimentacao.getCargo().setId(newAtoMovimentacao.getCargo().getId());
            }
            if (newAtoMovimentacao.getCargo().getName() != null) {
                existingAtoMovimentacao.getCargo().setName(newAtoMovimentacao.getCargo().getName());
            }
            if (newAtoMovimentacao.getCargo().getDescricao() != null) {
                existingAtoMovimentacao.getCargo().setDescricao(newAtoMovimentacao.getCargo().getDescricao());
            }
        }
        if (newAtoMovimentacao.getDiariooficial() != null) {
            if (newAtoMovimentacao.getDiariooficial().getId() != null) {
                existingAtoMovimentacao.getDiariooficial().setId(newAtoMovimentacao.getDiariooficial().getId());
            }
            if (newAtoMovimentacao.getDiariooficial().getName() != null) {
                existingAtoMovimentacao.getDiariooficial().setName(newAtoMovimentacao.getDiariooficial().getName());
            }
            if (newAtoMovimentacao.getDiariooficial().getDescricao() != null) {
                existingAtoMovimentacao.getDiariooficial().setDescricao(newAtoMovimentacao.getDiariooficial().getDescricao());
            }
        }
        if (newAtoMovimentacao.getDataEfeito() != null) {
            existingAtoMovimentacao.setDataEfeito(newAtoMovimentacao.getDataEfeito());
        }
    }

}

