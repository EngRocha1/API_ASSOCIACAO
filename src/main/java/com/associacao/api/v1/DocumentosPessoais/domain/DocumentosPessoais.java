package com.associacao.api.v1.DocumentosPessoais.domain;

import com.associacao.api.v1.DocumentosPessoais.domain.Listas.TipoDeDocumento;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;

/**
 * Entidade que representa os documentos pessoais de um servidor.
 * Armazena dados cadastrais e anexos digitais (RG, CPF, Título, CTPS, PIS/NIT e Reservista).
 * Esta classe é auditada para manter o histórico de alterações e documentos enviados.
 */
@Entity(name = "documentospessoais")
@Table(name = "documentospessoais")
@Audited
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class DocumentosPessoais extends Listagem {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "tipodedocumento_id")
    private TipoDeDocumento tipoDeDocumento;

    /** Dados do Registro Geral (RG) */
    @Column(name = "numero_rg")
    private String numeroRg;
    private String filenameRg;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataRg;
    private String naturalidade;
    private String nacionalidade;
    private LocalDate validadeRg;
    private String filiacaoPaiRg;
    private String filiacaoMaeRg;
    private LocalDate dataEmissaoRg;
    private String orgaoExpedidorRg;

    /** Dados do Cadastro de Pessoa Física (CPF) */
    @Column(name = "numero_cpf")
    private String numeroCpf;
    private String filenameCpf;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataCpf;
    private String situacaoCadastralCpf;
    private LocalDate dataInscricaoCpf;
    private String digitoVerificadorCpf;
    private LocalDate dataEmissaoCpf;

    /** Dados do Título de Eleitor */
    @Column(name = "numero_titulo_eleitor")
    private String numeroTituloEleitor;
    private String filenameTitulo;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataTitulo;
    private LocalDate dataEmissaoTitulo;
    private String zonaTitulo;
    private String secaoTitulo;

    /** Dados da Carteira de Trabalho (CTPS) */
    @Column(name = "numero_ctps")
    private String numeroCtps;
    private String filenameCtps;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataCtps;
    private LocalDate dataEmissaoCtps;
    private String orgaoExpedidorCtps;

    /** Dados de Identificação Social (PIS/PASEP, NIT ou NIS) */
    @Column(name = "numero_pispaspet_nis_nit")
    private String numeroPisNisNit;
    @Column(name = "data_cadastramento_pis")
    private LocalDate dataCadastramentoPis;
    private String filenamePis;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataPis;

    /** Dados do Certificado de Reservista */
    @Column(name = "numero_reservista")
    private String numeroReservista;
    private String filenameReservista;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataReservista;
    private LocalDate dataEmissaoReservista;
    private String regiaoMilitarReservista;

    /** Dados da CNH (Carteira Nacional de Habilitação) */
    @Column(name = "numero_cnh")
    private String numeroCnh;
    private String categoriaCnh;
    private LocalDate validadeCnh;
    private String filenameCnh;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataCnh;

    /** Dados de Estado Civil (Nascimento ou Casamento) */
    private String estadoCivil;
    private String filenameCertidaoCivil;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataCertidaoCivil;

    /** Comprovante de Residência */
    private String tipoResidencia;
    private String filenameComprovanteResidencia;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataComprovanteResidencia;

    /** Certidão de Quitação Eleitoral */
    private String filenameQuitacaoEleitoral;
    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] fileDataQuitacaoEleitoral;

    private boolean ativo;

    /**
     * Construtor completo para Documentos Pessoais padrão Brasil.
     * Inclui campos de dados e anexos para RG, CPF, Título, CTPS, PIS, Reservista,
     * CNH, Certidões, Comprovante de Residência e Quitação Eleitoral.
     */
    public DocumentosPessoais(Servidor servidor, TipoDeDocumento tipoDeDocumento,
                              String numeroRg, String filenameRg, byte[] fileDataRg, String naturalidade, String nacionalidade, LocalDate validadeRg, String filiacaoPaiRg, String filiacaoMaeRg, LocalDate dataEmissaoRg, String orgaoExpedidorRg,
                              String numeroCpf, String filenameCpf, byte[] fileDataCpf, String situacaoCadastralCpf, LocalDate dataInscricaoCpf, String digitoVerificadorCpf, LocalDate dataEmissaoCpf,
                              String numeroTituloEleitor, String filenameTitulo, byte[] fileDataTitulo, LocalDate dataEmissaoTitulo, String zonaTitulo, String secaoTitulo,
                              String numeroCtps, String filenameCtps, byte[] fileDataCtps, LocalDate dataEmissaoCtps, String orgaoExpedidorCtps,
                              String numeroPisNisNit, LocalDate dataCadastramentoPis, String filenamePis, byte[] fileDataPis,
                              String numeroReservista, String filenameReservista, byte[] fileDataReservista, LocalDate dataEmissaoReservista, String regiaoMilitarReservista,
                              String numeroCnh, String categoriaCnh, LocalDate validadeCnh, String filenameCnh, byte[] fileDataCnh,
                              String estadoCivil, String filenameCertidaoCivil, byte[] fileDataCertidaoCivil,
                              String tipoResidencia, String filenameComprovanteResidencia, byte[] fileDataComprovanteResidencia,
                              String filenameQuitacaoEleitoral, byte[] fileDataQuitacaoEleitoral,
                              boolean ativo) {
        super.setId(null);
        this.servidor = servidor;
        this.tipoDeDocumento = tipoDeDocumento;
        this.numeroRg = numeroRg;
        this.filenameRg = filenameRg;
        this.fileDataRg = fileDataRg;
        this.naturalidade = naturalidade;
        this.nacionalidade = nacionalidade;
        this.validadeRg = validadeRg;
        this.filiacaoPaiRg = filiacaoPaiRg;
        this.filiacaoMaeRg = filiacaoMaeRg;
        this.dataEmissaoRg = dataEmissaoRg;
        this.orgaoExpedidorRg = orgaoExpedidorRg;
        this.numeroCpf = numeroCpf;
        this.filenameCpf = filenameCpf;
        this.fileDataCpf = fileDataCpf;
        this.situacaoCadastralCpf = situacaoCadastralCpf;
        this.dataInscricaoCpf = dataInscricaoCpf;
        this.digitoVerificadorCpf = digitoVerificadorCpf;
        this.dataEmissaoCpf = dataEmissaoCpf;
        this.numeroTituloEleitor = numeroTituloEleitor;
        this.filenameTitulo = filenameTitulo;
        this.fileDataTitulo = fileDataTitulo;
        this.dataEmissaoTitulo = dataEmissaoTitulo;
        this.zonaTitulo = zonaTitulo;
        this.secaoTitulo = secaoTitulo;
        this.numeroCtps = numeroCtps;
        this.filenameCtps = filenameCtps;
        this.fileDataCtps = fileDataCtps;
        this.dataEmissaoCtps = dataEmissaoCtps;
        this.orgaoExpedidorCtps = orgaoExpedidorCtps;
        this.numeroPisNisNit = numeroPisNisNit;
        this.dataCadastramentoPis = dataCadastramentoPis;
        this.filenamePis = filenamePis;
        this.fileDataPis = fileDataPis;
        this.numeroReservista = numeroReservista;
        this.filenameReservista = filenameReservista;
        this.fileDataReservista = fileDataReservista;
        this.dataEmissaoReservista = dataEmissaoReservista;
        this.regiaoMilitarReservista = regiaoMilitarReservista;
        this.numeroCnh = numeroCnh;
        this.categoriaCnh = categoriaCnh;
        this.validadeCnh = validadeCnh;
        this.filenameCnh = filenameCnh;
        this.fileDataCnh = fileDataCnh;
        this.estadoCivil = estadoCivil;
        this.filenameCertidaoCivil = filenameCertidaoCivil;
        this.fileDataCertidaoCivil = fileDataCertidaoCivil;
        this.tipoResidencia = tipoResidencia;
        this.filenameComprovanteResidencia = filenameComprovanteResidencia;
        this.fileDataComprovanteResidencia = fileDataComprovanteResidencia;
        this.filenameQuitacaoEleitoral = filenameQuitacaoEleitoral;
        this.fileDataQuitacaoEleitoral = fileDataQuitacaoEleitoral;
        this.ativo = ativo;
    }
}


