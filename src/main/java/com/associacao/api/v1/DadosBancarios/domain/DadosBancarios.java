package com.associacao.api.v1.DadosBancarios.domain;
import com.associacao.api.v1.DadosBancarios.domain.Listas.Banco;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.classes.Listagem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;


@Entity(name = "dadosbancarios")
@Table(name = "dadosbancarios")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
@NoArgsConstructor
public class DadosBancarios extends Listagem {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    private String conta;
    private String agencia;
    private String chavepix;

    @Enumerated(EnumType.STRING)
    private DadosBancarios.TipoChave tipo;

    public enum TipoChave {
        CPF, CNPJ, CELULAR, ALEATORIA
    }

    private boolean ativo;
    public DadosBancarios(         String conta,
                                   String agencia,
                                   String chavepix,
                                   TipoChave tipo,
                                   Banco banco,
                                   Servidor servidor,
                                   boolean ativo) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        this.conta = conta;
        this.agencia = agencia;
        this.chavepix = chavepix;
        this.tipo = tipo;
        this.banco = banco;
        this.servidor = servidor;
        this.setAtivo(ativo);
    }

}
