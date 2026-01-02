package com.associacao.api.v1.DadosBancarios.service;

import com.associacao.api.v1.DadosBancarios.domain.DadosBancarios;
import com.associacao.api.v1.DadosBancarios.repository.DadosBancariosRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DadosBancariosService extends AbstractBaseService<DadosBancarios, String> {

    @Autowired
    public DadosBancariosService(DadosBancariosRepository repository) {
        super(repository);
    }

    @Override
    public DadosBancarios findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(DadosBancarios dadosBancarios) {
        dadosBancarios.setAtivo(false);
    }

    @Transactional
    public DadosBancarios update(String id, DadosBancarios dadosBancarios) {
        try {
            DadosBancarios existingDadosBancarios = validarId(id);
            updateEntityFields(existingDadosBancarios, dadosBancarios);
            return repository.save(existingDadosBancarios);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }
    public void updateEntityFields(DadosBancarios existingDadosBancarios, DadosBancarios newDadosBancarios) {
        if (newDadosBancarios.getBanco() != null) {
            if (newDadosBancarios.getBanco().getId() != null) {
                existingDadosBancarios.getBanco().setId(newDadosBancarios.getBanco().getId());
            }
            if (newDadosBancarios.getBanco().getName() != null) {
                existingDadosBancarios.getBanco().setName(newDadosBancarios.getBanco().getName());
            }
            if (newDadosBancarios.getBanco().getDescricao() != null) {
                existingDadosBancarios.getBanco().setDescricao(newDadosBancarios.getBanco().getDescricao());
            }
        }

        if (newDadosBancarios.getConta() != null) {
            existingDadosBancarios.setConta(newDadosBancarios.getConta());
        }
        if (newDadosBancarios.getAgencia() != null) {
            existingDadosBancarios.setAgencia(newDadosBancarios.getAgencia());
        }
        if (newDadosBancarios.getChavepix() != null) {
            existingDadosBancarios.setChavepix(newDadosBancarios.getChavepix());
        }
        if (newDadosBancarios.getTipo() != null) {
            existingDadosBancarios.setTipo(newDadosBancarios.getTipo());
        }

        if (newDadosBancarios.getServidor() != null) {
            if (newDadosBancarios.getServidor().getId() != null) {
                existingDadosBancarios.getServidor().setId(newDadosBancarios.getServidor().getId());
            }
            if (newDadosBancarios.getServidor().getName() != null) {
                existingDadosBancarios.getServidor().setName(newDadosBancarios.getServidor().getName());
            }
        }
        if (newDadosBancarios.isAtivo() != existingDadosBancarios.isAtivo()) {
            existingDadosBancarios.setAtivo(newDadosBancarios.isAtivo());
        }
    }

}
