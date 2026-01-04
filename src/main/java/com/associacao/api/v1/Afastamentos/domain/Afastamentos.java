package com.associacao.api.v1.Afastamentos.domain;

import com.associacao.api.v1.Afastamentos.domain.Listas.CID;
import com.associacao.api.v1.Afastamentos.domain.Listas.FluxoAprovacao;
import com.associacao.api.v1.Afastamentos.domain.Listas.Suspensao;
import com.associacao.api.v1.Afastamentos.domain.Listas.TipoAfastamento;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;


@Entity(name = "afastamentos")
@Table(name = "afastamentos")
@Audited
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Afastamentos extends Listagem {


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cid_id", nullable = true)
    private CID cid;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fluxo_id", nullable = false)
    private FluxoAprovacao fluxo;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoafastamento_id", nullable = false)
    private TipoAfastamento tipoafastamento;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suspensao_id", nullable = true)
    private Suspensao suspensao;

    private LocalDate dataReferencia;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;

    @Enumerated(EnumType.STRING)
    private Status statusTipo;

    public enum Status {
        SEM_DATA, FRUIDA, A_FRUIR, FRUINDO
    }

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao solicitacaoTipo;

    public enum StatusSolicitacao {
        AGUARDANDO, APROVADA, APROVADA_ALTERADA, CANCELADA, SUSPENSA, INTERROMPIDA
    }

    @Enumerated(EnumType.STRING)
    private StatusRequerimento requerimentoTipo;

    public enum StatusRequerimento {
        AGUARDANDO, APROVADA, APROVADA_ALTERADA, CANCELADA, SUSPENSA, INTERROMPIDA
    }

    @Enumerated(EnumType.STRING)
    private StatusSIAPI siapiTipo;

    public enum StatusSIAPI {
        AGUARDANDO, APROVADA, APROVADA_ALTERADA, CANCELADA, SUSPENSA, INTERROMPIDA
    }

    private String justificativa;

    private String numeroSEI;
    private boolean ativo;
    public Afastamentos(LocalDate dataReferencia,
                        LocalDate periodoInicio,
                        LocalDate periodoFim,
                        Status statusTipo,
                        StatusSolicitacao solicitacaoTipo,
                        StatusRequerimento requerimentoTipo,
                        StatusSIAPI siapiTipo,
                        String justificativa,
                        String numeroSEI,
                        CID cid,
                        Suspensao suspensao,
                        FluxoAprovacao fluxoAprovacao,
                        TipoAfastamento tipoAfastamento,
                        Servidor servidor,
                        boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        this.dataReferencia = dataReferencia;
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
        this.statusTipo = statusTipo;
        this.solicitacaoTipo = solicitacaoTipo;
        this.siapiTipo = siapiTipo;
        this.requerimentoTipo = requerimentoTipo;
        this.justificativa = justificativa;
        this.numeroSEI = numeroSEI;
        this.cid = cid;
        this.suspensao = suspensao;
        this.fluxo = fluxoAprovacao;
        this.tipoafastamento = tipoAfastamento;
        this.servidor = servidor;
        this.setAtivo(ativo);
    }
}

