package com.example.work3.v1.Afastamentos.domain.Listas;

import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Table(name="cid")
@Entity(name="cid")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class CID extends Listagem {
    private String codigo;

    public CID(String codigo, String name, String descricao, boolean ativo) {
        super.setId(null);
        super.setName(name);
        super.setDescricao(descricao);
        super.setAtivo(ativo);
        this.codigo = codigo;
    }
}

