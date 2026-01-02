package com.associacao.api.v1.InformacoesAssentamento.service;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.Cargo;
import com.associacao.api.v1.InformacoesAssentamento.repository.CargoRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CargoService extends AbstractBaseService<Cargo, String> {

    @Autowired
    public CargoService(CargoRepository repository) {
        super(repository);
    }

    @Override
    public Cargo findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(Cargo cargo) {
        cargo.setAtivo(false);
    }

    @Transactional
    public Cargo update(String id, Cargo cargo) {
        try {
            Cargo existingCargo = validarId(id);
            updateEntityFields(existingCargo, cargo);
            return repository.save(existingCargo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(Cargo existingCargo, Cargo newCargo) {
        if (newCargo.getName() != null) {
            existingCargo.setName(newCargo.getName());
        }
        if (newCargo.isAtivo() != existingCargo.isAtivo()) {
            existingCargo.setAtivo(newCargo.isAtivo());
        }
    }
}
