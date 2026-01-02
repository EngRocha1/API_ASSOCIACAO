package com.associacao.api.v1.InformacoesSensiveis.domain.Listas;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="genero")
@Entity(name="genero")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Genero extends Listagem {

    public Genero(String Id,String name, String descricao, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }
}
