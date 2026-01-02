package com.example.work3.v1.InformacoesSensiveis.domain.Listas;

import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLRestriction;

@Table(name="doencascronicasraras")
@Entity(name="doencascronicasraras")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class DoencasCronicasRaras extends Listagem {

    public DoencasCronicasRaras(String Id,String name, String descricao, boolean ativo) {
        super.setId(null);
        super.setName(name);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }

}