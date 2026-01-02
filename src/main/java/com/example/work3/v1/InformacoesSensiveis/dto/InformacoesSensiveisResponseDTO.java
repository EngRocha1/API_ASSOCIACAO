package com.example.work3.v1.InformacoesSensiveis.dto;

import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

/**
 * dto para resposta de Informações Sensíveis.
 * Contém apenas os IDs e os respectivos nomes para evitar informações desnecessárias.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class InformacoesSensiveisResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String servidorId;
    private String servidorNome;
    private String generoId;
    private String generoNome;
    private String nomeSocial;
    private String estadoCivilId;
    private String estadoCivilNome;
    private String racaEtniaId;
    private String racaEtniaNome;
    private String corPeleId;
    private String corPeleNome;
    private String orientacaoSexualId;
    private String orientacaoSexualNome;
    private String expressaoDegeneroId;
    private String expressaoDegeneroNome;
    private String pronomePreferidoId;
    private String pronomePreferidoNome;
    private String geracaoId;
    private String geracaoNome;
    private String nacionalidadeId;
    private String nacionalidadeNome;
    private boolean possuiDeficiencia;
    private boolean fazUsoCordao;
    private boolean possuiDoencaCronicaRara;
    private boolean ehNeurodivergente;
    private String cordaoId;
    private String cordaoNome;
    private String deficienciaId;
    private String deficienciaNome;
    private String doencasCronicasRarasId;
    private String doencasCronicasRarasNome;
    private String neurodiversidadeId;
    private String neurodiversidadeNome;
    private boolean ativo;
}