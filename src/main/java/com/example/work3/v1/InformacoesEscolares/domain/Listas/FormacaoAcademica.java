package com.example.work3.v1.InformacoesEscolares.domain.Listas;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity(name = "formacao_academica")
@Table(name = "formacao_academica")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class FormacaoAcademica extends Listagem {

    public FormacaoAcademica(String id,String name, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setAtivo(ativo);
    }
}

