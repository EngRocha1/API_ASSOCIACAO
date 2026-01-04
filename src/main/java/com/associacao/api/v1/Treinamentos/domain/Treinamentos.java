package com.associacao.api.v1.Treinamentos.domain;

import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import com.associacao.api.v1.Treinamentos.domain.Listas.TreinamentoCurso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Entidade que representa um treinamento realizado por um servidor.
 */
@Entity(name = "treinamentos")
@Table(name = "treinamentos")
@Audited
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SQLRestriction(value = "ativo = true")
public class Treinamentos extends Listagem {

    /**
     * Servidor que realizou o treinamento.
     */
    @ManyToOne
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    /**
     * Tipo de aprendizado do treinamento (presencial, híbrido, remoto).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_aprendizado")
    private TipoAprendizado tipoAprendizado;

    public enum TipoAprendizado {
        PRESENCIAL, HIBRIDO, REMOTO
    }

    /**
     * Lista de cursos realizados no treinamento.
     */
    @ManyToMany
    @JoinTable(
            name = "treinamento_treinamento_curso",
            joinColumns = @JoinColumn(name = "treinamento_id"),
            inverseJoinColumns = @JoinColumn(name = "treinamento_curso_id")
    )
    private List<TreinamentoCurso> cursos;

    /**
     * CPF do participante para emissão de certificado.
     */
    @Column(name = "cpf")
    private String cpf;

    /**
     * Email do participante para emissão de certificado.
     */
    @Column(name = "email")
    private String email;

    /**
     * Telefone do participante para emissão de certificado.
     */
    @Column(name = "telefone")
    private String telefone;

    /**
     * Instituição responsável pelo treinamento.
     */
    @Column(name = "instituicao_responsavel")
    private String instituicaoResponsavel;

    /**
     * Número do documento SEI do certificado.
     */
    @Column(name = "numero_doc_sei")
    private String numeroDocSei;

    /**
     * Gênero do participante.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    public enum Genero {
        MASCULINO, FEMININO, OUTRO
    }

    /**
     * Data de nascimento do participante.
     */
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /**
     * Indica se o treinamento está ativo.
     */
    private boolean ativo;

    /**
     * Construtor completo para a entidade Treinamentos.
     *
     * @param servidor              Servidor que realizou o treinamento.
     * @param tipoAprendizado       Tipo de aprendizado do treinamento.
     * @param cursos                Lista de cursos realizados no treinamento.
     * @param cpf                   CPF do participante.
     * @param email                 Email do participante.
     * @param telefone              Telefone do participante.
     * @param instituicaoResponsavel Instituição responsável.
     * @param numeroDocSei          Número do documento SEI.
     * @param genero                Gênero do participante.
     * @param dataNascimento        Data de nascimento do participante.
     * @param ativo                 Indica se o treinamento está ativo.
     */
    public Treinamentos(
            Servidor servidor,
            TipoAprendizado tipoAprendizado,
            List<TreinamentoCurso> cursos,
            String cpf,
            String email,
            String telefone,
            String instituicaoResponsavel,
            String numeroDocSei,
            Genero genero,
            LocalDate dataNascimento,
            boolean ativo
    ) {
        super.setId(null);
        this.servidor = servidor;
        this.tipoAprendizado = tipoAprendizado;
        this.cursos = cursos;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.instituicaoResponsavel = instituicaoResponsavel;
        this.numeroDocSei = numeroDocSei;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.ativo = ativo;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Treinamentos that = (Treinamentos) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}