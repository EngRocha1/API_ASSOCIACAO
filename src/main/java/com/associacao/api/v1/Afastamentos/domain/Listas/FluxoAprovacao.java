package com.associacao.api.v1.Afastamentos.domain.Listas;

import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Table(name="fluxoaprovacao")
@Entity(name="fluxoaprovacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class FluxoAprovacao extends Listagem {


    private LocalDate dataAprovacao;
    private LocalDate dataSolicitacao;
    private String aprovadoPor;
    private String solicitadoPor;


    @Enumerated(EnumType.STRING)
    private FluxoAprovacao.StatusAprovacao aprovacaoTipo;


    public enum StatusAprovacao {
        Aprovada, Reprovada, Cancelada
    }

    
    public FluxoAprovacao(String id,
                          @NotBlank String name,
                          String descricao,
                          StatusAprovacao aprovacaoTipo,
                          LocalDate dataAprovacao,
                          LocalDate dataSolicitacao,
                          String aprovadoPor,
                          String solicitadoPor,
                          boolean ativo)
    {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        super.setDescricao(descricao);
        this.aprovacaoTipo = aprovacaoTipo;
        this.dataAprovacao = dataAprovacao;
        this.aprovadoPor =aprovadoPor;
        this.dataSolicitacao =dataSolicitacao;
        this.solicitadoPor = solicitadoPor;
        super.setAtivo(ativo);
    }
}
