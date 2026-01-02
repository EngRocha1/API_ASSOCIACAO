package com.example.work3.v1.InformacoesEscolares.domain.Listas;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="curso")
@Entity(name="curso")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Curso extends Listagem {

    public Curso(String id,String name, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setAtivo(ativo);
    }

}