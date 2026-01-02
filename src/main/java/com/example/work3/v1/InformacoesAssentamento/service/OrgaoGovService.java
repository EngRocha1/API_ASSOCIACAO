package com.example.work3.v1.InformacoesAssentamento.service;

import com.example.work3.v1.InformacoesAssentamento.domain.Listas.OrgaoGov;
import com.example.work3.v1.InformacoesAssentamento.repository.OrgaoGovRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrgaoGovService extends AbstractBaseService<OrgaoGov, String> {

    @Autowired
    public OrgaoGovService(OrgaoGovRepository repository) {
        super(repository);
    }

    @Override
    public OrgaoGov findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(OrgaoGov orgaoGov) {
        orgaoGov.setAtivo(false);
    }

    @Transactional
    public OrgaoGov update(String id, OrgaoGov orgaoGov) {
        try {
            OrgaoGov existingOrgaoGov = validarId(id);
            updateEntityFields(existingOrgaoGov, orgaoGov);
            return repository.save(existingOrgaoGov);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(OrgaoGov existingOrgaoGov, OrgaoGov newOrgaoGov) {
        if (newOrgaoGov.getName() != null) {
            existingOrgaoGov.setName(newOrgaoGov.getName());
        }
        if (newOrgaoGov.isAtivo() != existingOrgaoGov.isAtivo()) {
            existingOrgaoGov.setAtivo(newOrgaoGov.isAtivo());
        }
    }
}
