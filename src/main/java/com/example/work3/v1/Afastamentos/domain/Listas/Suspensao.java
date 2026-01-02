package com.example.work3.v1.Afastamentos.domain.Listas;

import com.example.work3.v1.Afastamentos.domain.Afastamentos;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Table(name="suspensao")
@Entity(name="suspensao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Suspensao extends Listagem {

    @ManyToOne
    @JoinColumn(name = "afastamentos_id")
    private Afastamentos afastamentos;

    public Suspensao(String id,
                     String name,
                     String descricao,
                     Afastamentos afastamentos,
                     boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setDescricao(descricao);
        this.afastamentos = afastamentos;
        super.setAtivo(ativo);
    }
}
