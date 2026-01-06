package com.associacao.api.v1.InformacoesAssentamento.service;
import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.DiarioOficial;
import com.associacao.api.v1.InformacoesAssentamento.repository.DiarioOficialRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    protected void setAtivoFalse(DiarioOficial diariooficial) {
        diariooficial.setAtivo(false);
    }

    @Transactional
    public DiarioOficial update(String id, DiarioOficial diariooficial) {
        try {
            DiarioOficial existingDiarioOficial = validarId(id);
            updateEntityFields(existingDiarioOficial, diariooficial);
            return repository.save(existingDiarioOficial);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(DiarioOficial existingDiarioOficial, DiarioOficial newDiarioOficial) {
        if (newDiarioOficial.getName() != null) {
            existingDiarioOficial.setName(newDiarioOficial.getName());
        }
        if (newDiarioOficial.isAtivo() != existingDiarioOficial.isAtivo()) {
            existingDiarioOficial.setAtivo(newDiarioOficial.isAtivo());
        }
        if (newDiarioOficial.getDataPublicacao() != existingDiarioOficial.getDataPublicacao()) {
            existingDiarioOficial.setDataPublicacao(newDiarioOficial.getDataPublicacao());
        }
    }
}
