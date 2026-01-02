package com.example.work3.v1.InformacoesAssentamento.service;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.Vinculo;
import com.example.work3.v1.InformacoesAssentamento.repository.VinculoRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VinculoService extends AbstractBaseService<Vinculo, String> {

    @Autowired
    public VinculoService(VinculoRepository repository) {
        super(repository);
    }

    @Override
    public Vinculo findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Vinculo vinculo) {
        vinculo.setAtivo(false);
    }

    @Transactional
    public Vinculo update(String id, Vinculo vinculo) {
        try {
            Vinculo existingVinculo = validarId(id);
            updateEntityFields(existingVinculo, vinculo);
            return repository.save(existingVinculo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Vinculo existingVinculo, Vinculo newVinculo) {
        if (newVinculo.getName() != null) {
            existingVinculo.setName(newVinculo.getName());
        }
        if (newVinculo.isAtivo() != existingVinculo.isAtivo()) {
            existingVinculo.setAtivo(newVinculo.isAtivo());
        }
    }
}


