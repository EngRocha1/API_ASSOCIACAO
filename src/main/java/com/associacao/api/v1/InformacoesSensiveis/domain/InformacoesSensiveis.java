package com.associacao.api.v1.InformacoesSensiveis.domain;

import com.associacao.api.v1.InformacoesSensiveis.domain.Listas.*;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * Representa as informações sensíveis de um servidor, abrangendo dados de diversidade e inclusão.
 * Esta entidade armazena referências a diversas listas de classificação (gênero, raça, etc.)
 * e indicadores de sim/não para características específicas como deficiência e neurodiversidade.
 */
@Table(name="informacoes_sensiveis")
@Entity(name="informacoes_sensiveis")
@Audited
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class InformacoesSensiveis extends Auditable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @Column(name = "nome_social")
    private String nomeSocial;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "estado_civil_id")
    private EstadoCivil estadoCivil;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "raca_etnia_id")
    private RacaEtnia racaEtnia;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "orientacao_sexual_id")
    private OrientacaoSexual orientacaoSexual;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "expressao_degenero_id")
    private ExpressaoDegenero expressaoDegenero;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "pronome_preferido_id")
    private PronomePreferido pronomePreferido;

    @Enumerated(EnumType.STRING)
    @Column(name = "possui_deficiencia")
    private SimOuNao possuiDeficiencia;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "deficiencias_id")
    private Deficiencias deficiencias;

    @Enumerated(EnumType.STRING)
    @Column(name = "faz_uso_cordao")
    private SimOuNao fazUsoCordao;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "cordao_id")
    private Cordao cordao;

    @Enumerated(EnumType.STRING)
    @Column(name = "possui_doenca_cronica_rara")
    private SimOuNao possuiDoencaCronicaRara;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "doencas_cronicas_raras_id")
    private DoencasCronicasRaras doencasCronicasRaras;

    @Enumerated(EnumType.STRING)
    @Column(name = "eh_neurodivergente")
    private SimOuNao ehNeurodivergente;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "neurodiversidade_id")
    private Neurodiversidade neurodiversidade;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "geracao_id")
    private Geracao geracao;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "corpele_id")
    private CorPele corPele;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "nacionalidade_id")
    private Nacionalidade nacionalidade;

    public enum SimOuNao {
        SIM, NAO
    }

    private boolean ativo;

    /**
     * Construtor para criar uma nova instância de InformacoesSensiveis.
     *
     * @param servidor O servidor ao qual as informações sensíveis pertencem.
     * @param genero O gênero do servidor.
     * @param nomeSocial O nome social do servidor.
     * @param estadoCivil O estado civil do servidor.
     * @param racaEtnia A raça/etnia do servidor.
     * @param orientacaoSexual A orientação sexual do servidor.
     * @param expressaoDegenero A expressão de gênero do servidor.
     * @param pronomePreferido O pronome preferido do servidor.
     * @param possuiDeficiencia Indica se o servidor possui deficiência.
     * @param deficiencias O tipo de deficiência do servidor (se aplicável).
     * @param fazUsoCordao Indica se o servidor faz uso de cordão de identificação.
     * @param cordao O tipo de cordão utilizado pelo servidor (se aplicável).
     * @param possuiDoencaCronicaRara Indica se o servidor possui doença crônica ou rara.
     * @param doencasCronicasRaras O tipo de doença crônica ou rara (se aplicável).
     * @param ehNeurodivergente Indica se o servidor é neurodivergente.
     * @param neurodiversidade O tipo de neurodiversidade (se aplicável).
     * @param geracao A geração a que o servidor pertence.
     * @param nacionalidade A nacionalidade do servidor.
     * @param ativo O status de atividade da informação.
     */
    public InformacoesSensiveis(
            Servidor servidor,
            Genero genero,
            CorPele corPele,
            String nomeSocial,
            EstadoCivil estadoCivil,
            RacaEtnia racaEtnia,
            OrientacaoSexual orientacaoSexual,
            ExpressaoDegenero expressaoDegenero,
            PronomePreferido pronomePreferido,
            SimOuNao possuiDeficiencia,
            Deficiencias deficiencias,
            SimOuNao fazUsoCordao,
            Cordao cordao,
            SimOuNao possuiDoencaCronicaRara,
            DoencasCronicasRaras doencasCronicasRaras,
            SimOuNao ehNeurodivergente,
            Neurodiversidade neurodiversidade,
            Geracao geracao,
            Nacionalidade nacionalidade,
            boolean ativo) {
        this.servidor = servidor;
        this.corPele = corPele;
        this.genero = genero;
        this.nomeSocial = nomeSocial;
        this.estadoCivil = estadoCivil;
        this.racaEtnia = racaEtnia;
        this.orientacaoSexual = orientacaoSexual;
        this.expressaoDegenero = expressaoDegenero;
        this.pronomePreferido = pronomePreferido;
        this.possuiDeficiencia = possuiDeficiencia;
        this.deficiencias = deficiencias;
        this.fazUsoCordao = fazUsoCordao;
        this.cordao = cordao;
        this.possuiDoencaCronicaRara = possuiDoencaCronicaRara;
        this.doencasCronicasRaras = doencasCronicasRaras;
        this.ehNeurodivergente = ehNeurodivergente;
        this.neurodiversidade = neurodiversidade;
        this.geracao = geracao;
        this.nacionalidade = nacionalidade;
        this.setAtivo(ativo);
    }
}
