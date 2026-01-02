package com.example.work3.v1.InformacoesEscolares.domain.Listas;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="nivel_ensino")
@Entity(name="nivel_ensino")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class NivelEnsino extends Listagem {

    public NivelEnsino(String id, String name, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setAtivo(ativo);
    }
}