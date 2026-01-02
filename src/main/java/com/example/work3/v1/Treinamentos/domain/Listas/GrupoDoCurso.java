package com.example.work3.v1.Treinamentos.domain.Listas;

import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Table(name = "grupo_do_curso")
@Entity(name = "GrupoDoCurso")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SQLRestriction(value = "ativo = true")
public class GrupoDoCurso extends Listagem {

    public GrupoDoCurso(String id, String name, boolean ativo) {
        super.setId(null);
        super.setName(name);
        super.setAtivo(ativo);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        GrupoDoCurso that = (GrupoDoCurso) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}