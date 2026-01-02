package com.example.work3.v1.InformacoesSensiveis.domain.Listas;

import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="expressao_de_genero")
@Entity(name="expressao_de_genero")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class ExpressaoDegenero extends Listagem {

    public ExpressaoDegenero(String Id,String name, String descricao, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }
}