package com.associacao.api.v1.InformacoesAssentamento.domain;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.*;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity(name = "informacoes_assentamento")
@Table(name = "informacoes_assentamento")
@Audited
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@SQLRestriction(value = "ativo = true")
public class InformacoesAssentamento extends Listagem {


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "lotacao_id")
    private Lotacao lotacao;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "orgaogov_id")
    private OrgaoGov orgaogov;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "superintendencia_id")
    private Superintendencia superintendencia;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "diretoria_id")
    private Diretoria diretoria;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "diariooficial_id")
    private DiarioOficial diariooficial;

    private String matriculaInstitucional;
    private String emailInstitucionalGov;
    private boolean ativo;

    public InformacoesAssentamento(
            Servidor servidor,
            Lotacao lotacao,
            DiarioOficial diariooficial,
            Superintendencia superintendencia,
            OrgaoGov orgaogov,
            Diretoria diretoria,
            String matriculaInstitucional,
            String emailInstitucionalGov,
            boolean ativo) {
        super.setId(null);
        this.matriculaInstitucional = matriculaInstitucional;
        this.emailInstitucionalGov = emailInstitucionalGov;
        this.orgaogov = orgaogov;
        this.superintendencia = superintendencia;
        this.diretoria = diretoria;
        this.diariooficial = diariooficial;
        this.lotacao = lotacao;
        this.servidor = servidor;
        this.setAtivo(ativo);
    }
}