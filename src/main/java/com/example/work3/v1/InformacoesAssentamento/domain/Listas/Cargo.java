package com.example.work3.v1.InformacoesAssentamento.domain.Listas;

import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Table(name = "cargo")
@Entity(name = "cargo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SQLRestriction(value = "ativo = true")
public class Cargo extends Listagem {

    public Cargo(String id, String name, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
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
        Cargo cargo = (Cargo) o;
        return getId() != null && Objects.equals(getId(), cargo.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}