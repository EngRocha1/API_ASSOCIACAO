package com.associacao.api.v1.InformacoesAssentamento.domain.Listas;

import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "diariooficial")
@Entity(name = "diariooficial")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SQLRestriction(value = "ativo = true")
public class DiarioOficial extends Listagem {

    @ManyToOne
    @JoinColumn(name = "simbolo_id")
    private Simbolo simbolo;

    @ManyToOne
    @JoinColumn(name = "vinculo_id")
    private Vinculo vinculo;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    private LocalDate dataPublicacao;
    private LocalDate dataEfeito;

    @Enumerated(EnumType.STRING)
    private NomeacaoExoneracao tipo;

    public enum NomeacaoExoneracao {
        NOMEACAO, EXONERACAO, DISPOSICAO
    }

    public DiarioOficial(String id, String name, NomeacaoExoneracao tipo, boolean ativo, Simbolo simbolo, Vinculo vinculo, Cargo cargo, LocalDate dataefeito, LocalDate datapublicacao) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setAtivo(ativo);
        this.simbolo = simbolo;
        this.vinculo = vinculo;
        this.cargo = cargo;
        this.tipo = tipo;
        this.dataEfeito = dataefeito;
        this.dataPublicacao = datapublicacao;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DiarioOficial that = (DiarioOficial) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}