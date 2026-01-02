package com.example.work3.v1.InformacoesSensiveis.domain.Listas;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="raca_etnia")
@Entity(name="raca_etnia")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class RacaEtnia extends Listagem {

    public RacaEtnia(String id,String name, String descricao, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }

}