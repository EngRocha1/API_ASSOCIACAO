package com.example.work3.v1.DocumentosPessoais.domain;

import com.example.work3.v1.DocumentosPessoais.domain.Listas.TipoDeDocumento;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.proxy.HibernateProxy;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "documentospessoais")
@Table(name = "documentospessoais")
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

    @ManyToOne
    @JoinColumn(name = "tipodedocumento_id")
    private TipoDeDocumento tipoDeDocumento;

    @Column(name = "numero_rg")
    private String numeroRg;

    @Column(name = "naturalidade")
    private String naturalidade;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "validade_rg")
    private LocalDate validadeRg;

    @Column(name = "filiacao_pai_rg")
    private String filiacaoPaiRg;

    @Column(name = "filiacao_mae_rg")
    private String filiacaoMaeRg;

    @Column(name = "data_emissao_rg")
    private LocalDate dataEmissaoRg;

    @Column(name = "orgao_expedidor_rg")
    private String orgaoExpedidorRg;

    @Column(name = "numero_cpf")
    private String numeroCpf;

    @Column(name = "situacao_cadastral_cpf")
    private String situacaoCadastralCpf;

    @Column(name = "data_inscricao_cpf")
    private LocalDate dataInscricaoCpf;

    @Column(name = "digito_verificador_cpf")
    private String digitoVerificadorCpf;

    @Column(name = "data_emissao_cpf")
    private LocalDate dataEmissaoCpf;

    @Column(name = "numero_titulo_eleitor")
    private String numeroTituloEleitor;

    @Column(name = "data_emissao_titulo")
    private LocalDate dataEmissaoTitulo;

    @Column(name = "zona_titulo")
    private String zonaTitulo;

    @Column(name = "secao_titulo")
    private String secaoTitulo;

    @Column(name = "numero_ctps")
    private String numeroCtps;

    @Column(name = "data_emissao_ctps")
    private LocalDate dataEmissaoCtps;

    @Column(name = "orgao_expedidor_ctps")
    private String orgaoExpedidorCtps;

    @Column(name = "numero_pis_nis")
    private String numeroPisNis;

    @Column(name = "numero_reservista")
    private String numeroReservista;

    @Column(name = "data_emissao_reservista")
    private LocalDate dataEmissaoReservista;

    @Column(name = "regiao_militar_reservista")
    private String regiaoMilitarReservista;

    @Column(name = "ativo")
    private boolean ativo;

    public DocumentosPessoais(Servidor servidor, TipoDeDocumento tipoDeDocumento, String numeroRg, String naturalidade, String nacionalidade, LocalDate validadeRg, String filiacaoPaiRg, String filiacaoMaeRg, LocalDate dataEmissaoRg, String orgaoExpedidorRg, String numeroCpf, String situacaoCadastralCpf, LocalDate dataInscricaoCpf, String digitoVerificadorCpf, LocalDate dataEmissaoCpf, String numeroTituloEleitor, LocalDate dataEmissaoTitulo, String zonaTitulo, String secaoTitulo, String numeroCtps, LocalDate dataEmissaoCtps, String orgaoExpedidorCtps, String numeroPisNis, String numeroReservista, LocalDate dataEmissaoReservista, String regiaoMilitarReservista, boolean ativo) {
        super.setId(null);
        this.servidor = servidor;
        this.tipoDeDocumento = tipoDeDocumento;
        this.numeroRg = numeroRg;
        this.naturalidade = naturalidade;
        this.nacionalidade = nacionalidade;
        this.validadeRg = validadeRg;
        this.filiacaoPaiRg = filiacaoPaiRg;
        this.filiacaoMaeRg = filiacaoMaeRg;
        this.dataEmissaoRg = dataEmissaoRg;
        this.orgaoExpedidorRg = orgaoExpedidorRg;
        this.numeroCpf = numeroCpf;
        this.situacaoCadastralCpf = situacaoCadastralCpf;
        this.dataInscricaoCpf = dataInscricaoCpf;
        this.digitoVerificadorCpf = digitoVerificadorCpf;
        this.dataEmissaoCpf = dataEmissaoCpf;
        this.numeroTituloEleitor = numeroTituloEleitor;
        this.dataEmissaoTitulo = dataEmissaoTitulo;
        this.zonaTitulo = zonaTitulo;
        this.secaoTitulo = secaoTitulo;
        this.numeroCtps = numeroCtps;
        this.dataEmissaoCtps = dataEmissaoCtps;
        this.orgaoExpedidorCtps = orgaoExpedidorCtps;
        this.numeroPisNis = numeroPisNis;
        this.numeroReservista = numeroReservista;
        this.dataEmissaoReservista = dataEmissaoReservista;
        this.regiaoMilitarReservista = regiaoMilitarReservista;
        this.ativo = ativo;
    }
}





