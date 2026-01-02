package com.associacao.api.v1.Treinamentos.domain.Listas;

import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Table(name = "treinamento_curso")
@Entity(name = "TreinamentoCurso")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SQLRestriction(value = "ativo = true")
public class TreinamentoCurso extends Listagem {

    @ManyToMany
    @JoinTable(
            name = "treinamento_curso_grupo",
            joinColumns = @JoinColumn(name = "treinamento_curso_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_do_curso_id")
    )
    @ToString.Exclude
    private List<GrupoDoCurso> gruposDoCurso;

    /**
     * Tipo do curso (comportamental, técnico).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_curso")
    private TipoCurso tipoCurso;

    public enum TipoCurso {
        COMPORTAMENTAL, TECNICO
    }

    /**
     * Número da turma do curso.
     */
    @Column(name = "turma_numero")
    private String turmaNumero;

    /**
     * Nome do instrutor do curso.
     */
    @Column(name = "instrutor")
    private String instrutor;

    /**
     * Conteúdo programático do curso.
     */
    @Column(name = "conteudo_curso", columnDefinition = "TEXT")
    private String conteudoCurso;

    /**
     * Carga horária do curso em horas.
     */
    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    public TreinamentoCurso(String id, String name, boolean ativo, List<GrupoDoCurso> gruposDoCurso, TipoCurso tipoCurso, String turmaNumero, String instrutor, String conteudoCurso, Integer cargaHoraria) {
        super.setId(null);
        super.setName(name);
        super.setAtivo(ativo);
        this.gruposDoCurso = gruposDoCurso;
        this.tipoCurso = tipoCurso;
        this.turmaNumero = turmaNumero;
        this.instrutor = instrutor;
        this.conteudoCurso = conteudoCurso;
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TreinamentoCurso that = (TreinamentoCurso) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}