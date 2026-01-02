package com.example.work3.v1.InformacoesEscolares.domain;
import com.example.work3.v1.InformacoesEscolares.domain.Listas.*;
import com.example.work3.v1.Servidor.domain.Servidor;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity(name = "informacoes_escolares")
@Table(name = "informacoes_escolares")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
@NoArgsConstructor
public class InformacoesEscolares extends Listagem {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    @ManyToOne
    @JoinColumn(name = "instituicao_ensino_id")
    private InstituicaoEnsino instituicaoEnsino;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "periodo_do_dia_id")
    private Periodo periodoDoDia;

    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "formacao_academica_id")
    private FormacaoAcademica formacaoAcademica;

    @ManyToOne
    @JoinColumn(name = "status_formacao_id")
    private StatusFormacao statusFormacao;

    private String matriculaInstitucional;
    private LocalDate inicioCurso;
    private LocalDate terminoPrevisto;
    private Integer cargaHorariaTotal;
    private Integer cargaHorariaAtual;

    private boolean ativo;

    public InformacoesEscolares(Curso curso,
                                LocalDate inicioCurso,
                                Integer cargaHorariaAtual,
                                Integer cargaHorariaTotal,
                                FormacaoAcademica formacaoAcademica,
                                StatusFormacao statusFormacao,
                                InstituicaoEnsino instituicaoEnsino,
                                String matriculaInstitucional,
                                Periodo periodoDoDia,
                                Semestre semestre,
                                Servidor servidor,
                                LocalDate terminoPrevisto,
                                boolean ativo) {
        super.setId(null);
        this.curso = curso;
        this.inicioCurso = inicioCurso;
        this.cargaHorariaAtual = cargaHorariaAtual;
        this.cargaHorariaTotal = cargaHorariaTotal;
        this.formacaoAcademica = formacaoAcademica;
        this.statusFormacao = statusFormacao;
        this.instituicaoEnsino = instituicaoEnsino;
        this.matriculaInstitucional = matriculaInstitucional;
        this.periodoDoDia = periodoDoDia;
        this.semestre = semestre;
        this.servidor = servidor;
        this.terminoPrevisto = terminoPrevisto;
        this.setAtivo(ativo);
    }

}
