package com.associacao.api.v1.InformacoesEscolares.domain.Listas;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name="tipo_instituicao")
@Entity(name="tipo_instituicao")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@SQLRestriction(value = "ativo = true")
public class TipoInstituicao extends Listagem {

    public TipoInstituicao(String id, String name, boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setAtivo(ativo);
    }
}
