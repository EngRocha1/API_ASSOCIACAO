package com.associacao.api.v1.InformacoesSensiveis.domain.Listas;

import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="cor_pele")
@Entity(name="cor_pele")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class CorPele extends Listagem {

    public CorPele(String Id, String name, String descricao, boolean ativo) {
        super.setId(null);
        super.setName(name);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }
}
