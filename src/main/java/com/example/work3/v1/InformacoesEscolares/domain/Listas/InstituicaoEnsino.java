package com.example.work3.v1.InformacoesEscolares.domain.Listas;
import com.example.work3.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity(name = "instituicao_ensino")
@Table(name = "instituicao_ensino")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
@NoArgsConstructor
public class InstituicaoEnsino extends Listagem {

    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoInstituicao tipo;

    @Enumerated(EnumType.STRING)
    private NivelEnsino nivelEnsino;

    private LocalDate dataFundacao;
    private Integer numeroAlunos;
    private Integer numeroProfessores;
    private String site;
    private String redesSociais;


    public enum TipoInstituicao {
        PUBLICA, PRIVADA
    }

    public enum NivelEnsino {
        FUNDAMENTAL, MEDIO, SUPERIOR
    }
    public InstituicaoEnsino(String id,String name, String cnpj, String email, String endereco, String redesSociais, String site, String telefone, NivelEnsino nivelEnsino, LocalDate dataFundacao, Integer numeroAlunos, Integer numeroProfessores, TipoInstituicao tipo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        super.setName(name);
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.tipo = tipo;
        this.site = site;
        this.redesSociais = redesSociais;
        this.nivelEnsino = nivelEnsino;
        this.dataFundacao = dataFundacao;
        this.numeroAlunos = numeroAlunos;
        this.numeroProfessores = numeroProfessores;
    }
}