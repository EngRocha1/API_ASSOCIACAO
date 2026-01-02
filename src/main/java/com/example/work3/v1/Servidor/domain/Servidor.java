package com.example.work3.v1.Servidor.domain;
import com.example.work3.v1.Afastamentos.domain.Afastamentos;
import com.example.work3.v1.DadosBancarios.domain.DadosBancarios;
import com.example.work3.v1.DocumentosPessoais.domain.DocumentosPessoais;
import com.example.work3.v1.InformacoesAssentamento.domain.InformacoesAssentamento;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.InformacoesEscolares.domain.InformacoesEscolares;
import com.example.work3.v1.InformacoesSensiveis.domain.InformacoesSensiveis;
import com.example.work3.v1.Users.domain.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name="servidor")
@Table(name="servidor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Servidor extends Auditable  {

    private String name;
    private String cpf;
    private LocalDate dataNascimento;
    private boolean ativo;

    @OneToOne(mappedBy = "servidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private InformacoesSensiveis informacoesSensiveis;

    @OneToMany(mappedBy = "servidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<InformacoesEscolares> informacoesEscolares = new ArrayList<>();

    @OneToMany(mappedBy = "servidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Afastamentos> afastamentos = new ArrayList<>();



    @OneToMany(mappedBy = "servidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<InformacoesAssentamento> informacoesAssentamentos = new ArrayList<>();

    @OneToOne(mappedBy = "servidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private DocumentosPessoais documentosPessoais;

    @OneToOne(mappedBy = "servidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private DadosBancarios dadosBancarios;


    @Setter
    @Getter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users usuario;

    public boolean isAtivo() {
        if (usuario != null) {
            return usuario.getAtivo();
        } else {
            return false;
        }
    }


    private String filename;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;

    public Servidor(String Id,
                    String name,
                    String cpf,
                    LocalDate dataNascimento,
                    Users usuario,
                    boolean ativo){
        super.setId(null);
        this.name = name;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.usuario = usuario;
        this.setAtivo(ativo);

    }
}


