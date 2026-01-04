package com.associacao.api.v1.DocumentosPessoais.dto;

import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO para resposta de Informações de Documentos Pessoais.
 * Inclui os metadados dos documentos e os anexos correspondentes,
 * cobrindo o padrão de documentação brasileiro completo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DocumentosPessoaisResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String tipoDeDocumentoId;
    private String tipoDeDocumentoNome;
    private String tipoDeDocumentoDescricao;

    // --- RG ---
    private String numeroRg;
    private String filenameRg;
    private byte[] fileDataRg;
    private String naturalidade;
    private String nacionalidade;
    private LocalDate validadeRg;
    private String filiacaoPaiRg;
    private String filiacaoMaeRg;
    private LocalDate dataEmissaoRg;
    private String orgaoExpedidorRg;

    // --- CPF ---
    private String numeroCpf;
    private String filenameCpf;
    private byte[] fileDataCpf;
    private String situacaoCadastralCpf;
    private LocalDate dataInscricaoCpf;
    private String digitoVerificadorCpf;
    private LocalDate dataEmissaoCpf;

    // --- TÍTULO ---
    private String numeroTituloEleitor;
    private String filenameTitulo;
    private byte[] fileDataTitulo;
    private LocalDate dataEmissaoTitulo;
    private String zonaTitulo;
    private String secaoTitulo;

    // --- CTPS ---
    private String numeroCtps;
    private String filenameCtps;
    private byte[] fileDataCtps;
    private LocalDate dataEmissaoCtps;
    private String orgaoExpedidorCtps;

    // --- PIS / NIT / NIS ---
    private String numeroPisNis;
    private String numeroPisNisNit;
    private LocalDate dataCadastramentoPis;
    private String filenamePis;
    private byte[] fileDataPis;

    // --- RESERVISTA ---
    private String numeroReservista;
    private String filenameReservista;
    private byte[] fileDataReservista;
    private LocalDate dataEmissaoReservista;
    private String regiaoMilitarReservista;

    // --- CNH ---
    private String numeroCnh;
    private String categoriaCnh;
    private LocalDate validadeCnh;
    private String filenameCnh;
    private byte[] fileDataCnh;

    // --- CERTIDÕES (Nascimento/Casamento) ---
    private String estadoCivil;
    private String filenameCertidaoCivil;
    private byte[] fileDataCertidaoCivil;

    // --- RESIDÊNCIA ---
    private String tipoResidencia;
    private String filenameComprovanteResidencia;
    private byte[] fileDataComprovanteResidencia;

    // --- QUITAÇÃO ELEITORAL ---
    private String filenameQuitacaoEleitoral;
    private byte[] fileDataQuitacaoEleitoral;

    private boolean ativo;
    private String servidorId;
    private String servidorNome;
}