package com.associacao.api.v1.InformacoesSensiveis.domain.Listas;

import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLRestriction;

@Table(name="cordao")
@Entity(name="cordao")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Cordao extends Listagem {

    public Cordao(String Id, String name, String descricao, boolean ativo) {
        super.setId(null);
        super.setName(name);
        super.setDescricao(descricao);
        super.setAtivo(ativo);
    }
}