package com.associacao.api.v1.DocumentosPessoais.service;

import com.associacao.api.v1.DocumentosPessoais.domain.DocumentosPessoais;
import com.associacao.api.v1.DocumentosPessoais.repository.DocumentosPessoaisRepository;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service responsável pela lógica de negócio de Documentos Pessoais.
 * Gerencia a persistência de dados cadastrais e arquivos anexos para o padrão brasileiro completo.
 */
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

    /**
     * Atualiza os campos da entidade existente com os novos dados fornecidos.
     * Inclui verificações para CNH, Residência, Certidões e Quitação Eleitoral.
     */
    public void updateEntityFields(DocumentosPessoais existingDocumentosPessoais, DocumentosPessoais newDocumentosPessoais) {
        existingDocumentosPessoais.setServidor(newDocumentosPessoais.getServidor());
        existingDocumentosPessoais.setTipoDeDocumento(newDocumentosPessoais.getTipoDeDocumento());
        existingDocumentosPessoais.setAtivo(newDocumentosPessoais.isAtivo());

        // --- RG ---
        existingDocumentosPessoais.setNumeroRg(newDocumentosPessoais.getNumeroRg());
        existingDocumentosPessoais.setNaturalidade(newDocumentosPessoais.getNaturalidade());
        existingDocumentosPessoais.setNacionalidade(newDocumentosPessoais.getNacionalidade());
        existingDocumentosPessoais.setValidadeRg(newDocumentosPessoais.getValidadeRg());
        existingDocumentosPessoais.setFiliacaoPaiRg(newDocumentosPessoais.getFiliacaoPaiRg());
        existingDocumentosPessoais.setFiliacaoMaeRg(newDocumentosPessoais.getFiliacaoMaeRg());
        existingDocumentosPessoais.setDataEmissaoRg(newDocumentosPessoais.getDataEmissaoRg());
        existingDocumentosPessoais.setOrgaoExpedidorRg(newDocumentosPessoais.getOrgaoExpedidorRg());
        if (newDocumentosPessoais.getFileDataRg() != null) {
            existingDocumentosPessoais.setFilenameRg(newDocumentosPessoais.getFilenameRg());
            existingDocumentosPessoais.setFileDataRg(newDocumentosPessoais.getFileDataRg());
        }

        // --- CPF ---
        existingDocumentosPessoais.setNumeroCpf(newDocumentosPessoais.getNumeroCpf());
        existingDocumentosPessoais.setSituacaoCadastralCpf(newDocumentosPessoais.getSituacaoCadastralCpf());
        existingDocumentosPessoais.setDataInscricaoCpf(newDocumentosPessoais.getDataInscricaoCpf());
        existingDocumentosPessoais.setDigitoVerificadorCpf(newDocumentosPessoais.getDigitoVerificadorCpf());
        existingDocumentosPessoais.setDataEmissaoCpf(newDocumentosPessoais.getDataEmissaoCpf());
        if (newDocumentosPessoais.getFileDataCpf() != null) {
            existingDocumentosPessoais.setFilenameCpf(newDocumentosPessoais.getFilenameCpf());
            existingDocumentosPessoais.setFileDataCpf(newDocumentosPessoais.getFileDataCpf());
        }

        // --- TÍTULO ---
        existingDocumentosPessoais.setNumeroTituloEleitor(newDocumentosPessoais.getNumeroTituloEleitor());
        existingDocumentosPessoais.setDataEmissaoTitulo(newDocumentosPessoais.getDataEmissaoTitulo());
        existingDocumentosPessoais.setZonaTitulo(newDocumentosPessoais.getZonaTitulo());
        existingDocumentosPessoais.setSecaoTitulo(newDocumentosPessoais.getSecaoTitulo());
        if (newDocumentosPessoais.getFileDataTitulo() != null) {
            existingDocumentosPessoais.setFilenameTitulo(newDocumentosPessoais.getFilenameTitulo());
            existingDocumentosPessoais.setFileDataTitulo(newDocumentosPessoais.getFileDataTitulo());
        }

        // --- CTPS ---
        existingDocumentosPessoais.setNumeroCtps(newDocumentosPessoais.getNumeroCtps());
        existingDocumentosPessoais.setDataEmissaoCtps(newDocumentosPessoais.getDataEmissaoCtps());
        existingDocumentosPessoais.setOrgaoExpedidorCtps(newDocumentosPessoais.getOrgaoExpedidorCtps());
        if (newDocumentosPessoais.getFileDataCtps() != null) {
            existingDocumentosPessoais.setFilenameCtps(newDocumentosPessoais.getFilenameCtps());
            existingDocumentosPessoais.setFileDataCtps(newDocumentosPessoais.getFileDataCtps());
        }

        // --- PIS / NIT / NIS ---
        existingDocumentosPessoais.setNumeroPisNisNit(newDocumentosPessoais.getNumeroPisNisNit());
        existingDocumentosPessoais.setDataCadastramentoPis(newDocumentosPessoais.getDataCadastramentoPis());
        if (newDocumentosPessoais.getFileDataPis() != null) {
            existingDocumentosPessoais.setFilenamePis(newDocumentosPessoais.getFilenamePis());
            existingDocumentosPessoais.setFileDataPis(newDocumentosPessoais.getFileDataPis());
        }

        // --- RESERVISTA ---
        existingDocumentosPessoais.setNumeroReservista(newDocumentosPessoais.getNumeroReservista());
        existingDocumentosPessoais.setDataEmissaoReservista(newDocumentosPessoais.getDataEmissaoReservista());
        existingDocumentosPessoais.setRegiaoMilitarReservista(newDocumentosPessoais.getRegiaoMilitarReservista());
        if (newDocumentosPessoais.getFileDataReservista() != null) {
            existingDocumentosPessoais.setFilenameReservista(newDocumentosPessoais.getFilenameReservista());
            existingDocumentosPessoais.setFileDataReservista(newDocumentosPessoais.getFileDataReservista());
        }

        // --- CNH ---
        existingDocumentosPessoais.setNumeroCnh(newDocumentosPessoais.getNumeroCnh());
        existingDocumentosPessoais.setCategoriaCnh(newDocumentosPessoais.getCategoriaCnh());
        existingDocumentosPessoais.setValidadeCnh(newDocumentosPessoais.getValidadeCnh());
        if (newDocumentosPessoais.getFileDataCnh() != null) {
            existingDocumentosPessoais.setFilenameCnh(newDocumentosPessoais.getFilenameCnh());
            existingDocumentosPessoais.setFileDataCnh(newDocumentosPessoais.getFileDataCnh());
        }

        // --- CERTIDÕES ---
        existingDocumentosPessoais.setEstadoCivil(newDocumentosPessoais.getEstadoCivil());
        if (newDocumentosPessoais.getFileDataCertidaoCivil() != null) {
            existingDocumentosPessoais.setFilenameCertidaoCivil(newDocumentosPessoais.getFilenameCertidaoCivil());
            existingDocumentosPessoais.setFileDataCertidaoCivil(newDocumentosPessoais.getFileDataCertidaoCivil());
        }

        // --- RESIDÊNCIA ---
        existingDocumentosPessoais.setTipoResidencia(newDocumentosPessoais.getTipoResidencia());
        if (newDocumentosPessoais.getFileDataComprovanteResidencia() != null) {
            existingDocumentosPessoais.setFilenameComprovanteResidencia(newDocumentosPessoais.getFilenameComprovanteResidencia());
            existingDocumentosPessoais.setFileDataComprovanteResidencia(newDocumentosPessoais.getFileDataComprovanteResidencia());
        }

        // --- QUITAÇÃO ELEITORAL ---
        if (newDocumentosPessoais.getFileDataQuitacaoEleitoral() != null) {
            existingDocumentosPessoais.setFilenameQuitacaoEleitoral(newDocumentosPessoais.getFilenameQuitacaoEleitoral());
            existingDocumentosPessoais.setFileDataQuitacaoEleitoral(newDocumentosPessoais.getFileDataQuitacaoEleitoral());
        }
    }
}