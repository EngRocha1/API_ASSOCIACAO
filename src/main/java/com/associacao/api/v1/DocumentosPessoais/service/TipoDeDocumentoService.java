package com.associacao.api.v1.DocumentosPessoais.service;

import com.associacao.api.v1.DocumentosPessoais.domain.Listas.TipoDeDocumento;
import com.associacao.api.v1.DocumentosPessoais.repository.TipoDeDocumentoRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TipoDeDocumentoService extends AbstractBaseService<TipoDeDocumento, String> {

    @Autowired
    public TipoDeDocumentoService(TipoDeDocumentoRepository repository) {
        super(repository);
    }

    @Override
    public TipoDeDocumento findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(TipoDeDocumento tipoDeDocumento) {
        tipoDeDocumento.setAtivo(false);
    }

    @Transactional
    public TipoDeDocumento update(String id, TipoDeDocumento tipoDeDocumento) {
        try {
            TipoDeDocumento existingTipoDeDocumento = validarId(id);
            updateEntityFields(existingTipoDeDocumento, tipoDeDocumento);
            return repository.save(existingTipoDeDocumento);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(TipoDeDocumento existingTipoDeDocumento, TipoDeDocumento newTipoDeDocumento) {
        if (newTipoDeDocumento.getName() != null) {
            existingTipoDeDocumento.setName(newTipoDeDocumento.getName());
        }
        if (newTipoDeDocumento.isAtivo() != existingTipoDeDocumento.isAtivo()) {
            existingTipoDeDocumento.setAtivo(newTipoDeDocumento.isAtivo());
        }
    }
}
