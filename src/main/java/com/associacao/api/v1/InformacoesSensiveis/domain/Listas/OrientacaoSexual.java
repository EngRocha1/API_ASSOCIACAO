package com.associacao.api.v1.InformacoesSensiveis.domain.Listas;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="orientacao_sexual")
@Entity(name="orientacao_sexual")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class OrientacaoSexual extends Listagem {

    public OrientacaoSexual(String Id,String name, String descricao, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }
}
