package com.example.work3.v1.DocumentosPessoais.service;

import com.example.work3.v1.DocumentosPessoais.domain.DocumentosPessoais;
import com.example.work3.v1.DocumentosPessoais.repository.DocumentosPessoaisRepository;
import com.example.work3.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DocumentosPessoaisService extends AbstractBaseService<DocumentosPessoais, String> {

    @Autowired
    public DocumentosPessoaisService(DocumentosPessoaisRepository repository) {
        super(repository);
    }

    @Override
    public DocumentosPessoais findById(String id) {
        return super.validarId(id);
    }

    @Override
    protected void setAtivoFalse(DocumentosPessoais documentosPessoais) {
        documentosPessoais.setAtivo(false);
    }

    @Transactional
    public DocumentosPessoais update(String id, DocumentosPessoais newDocumentosPessoais) {
        try {
            DocumentosPessoais existingDocumentosPessoais = validarId(id);
            updateEntityFields(existingDocumentosPessoais, newDocumentosPessoais);
            return repository.save(existingDocumentosPessoais);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.", e);
        }
    }

    public void updateEntityFields(DocumentosPessoais existingDocumentosPessoais, DocumentosPessoais newDocumentosPessoais) {
        existingDocumentosPessoais.setTipoDeDocumento(newDocumentosPessoais.getTipoDeDocumento());
        existingDocumentosPessoais.setNumeroRg(newDocumentosPessoais.getNumeroRg());
        existingDocumentosPessoais.setNaturalidade(newDocumentosPessoais.getNaturalidade());
        existingDocumentosPessoais.setNacionalidade(newDocumentosPessoais.getNacionalidade());
        existingDocumentosPessoais.setValidadeRg(newDocumentosPessoais.getValidadeRg());
        existingDocumentosPessoais.setFiliacaoPaiRg(newDocumentosPessoais.getFiliacaoPaiRg());
        existingDocumentosPessoais.setFiliacaoMaeRg(newDocumentosPessoais.getFiliacaoMaeRg());
        existingDocumentosPessoais.setDataEmissaoRg(newDocumentosPessoais.getDataEmissaoRg());
        existingDocumentosPessoais.setOrgaoExpedidorRg(newDocumentosPessoais.getOrgaoExpedidorRg());
        existingDocumentosPessoais.setNumeroCpf(newDocumentosPessoais.getNumeroCpf());
        existingDocumentosPessoais.setSituacaoCadastralCpf(newDocumentosPessoais.getSituacaoCadastralCpf());
        existingDocumentosPessoais.setDataInscricaoCpf(newDocumentosPessoais.getDataInscricaoCpf());
        existingDocumentosPessoais.setDigitoVerificadorCpf(newDocumentosPessoais.getDigitoVerificadorCpf());
        existingDocumentosPessoais.setDataEmissaoCpf(newDocumentosPessoais.getDataEmissaoCpf());
        existingDocumentosPessoais.setNumeroTituloEleitor(newDocumentosPessoais.getNumeroTituloEleitor());
        existingDocumentosPessoais.setDataEmissaoTitulo(newDocumentosPessoais.getDataEmissaoTitulo());
        existingDocumentosPessoais.setZonaTitulo(newDocumentosPessoais.getZonaTitulo());
        existingDocumentosPessoais.setSecaoTitulo(newDocumentosPessoais.getSecaoTitulo());
        existingDocumentosPessoais.setNumeroCtps(newDocumentosPessoais.getNumeroCtps());
        existingDocumentosPessoais.setDataEmissaoCtps(newDocumentosPessoais.getDataEmissaoCtps());
        existingDocumentosPessoais.setOrgaoExpedidorCtps(newDocumentosPessoais.getOrgaoExpedidorCtps());
        existingDocumentosPessoais.setNumeroPisNis(newDocumentosPessoais.getNumeroPisNis());
        existingDocumentosPessoais.setNumeroReservista(newDocumentosPessoais.getNumeroReservista());
        existingDocumentosPessoais.setDataEmissaoReservista(newDocumentosPessoais.getDataEmissaoReservista());
        existingDocumentosPessoais.setRegiaoMilitarReservista(newDocumentosPessoais.getRegiaoMilitarReservista());
        existingDocumentosPessoais.setAtivo(newDocumentosPessoais.isAtivo());
        existingDocumentosPessoais.setServidor(newDocumentosPessoais.getServidor());
}
}