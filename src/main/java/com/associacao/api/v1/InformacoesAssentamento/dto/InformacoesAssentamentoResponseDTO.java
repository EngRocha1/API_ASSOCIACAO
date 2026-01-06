package com.associacao.api.v1.InformacoesAssentamento.dto;

import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

/**
 * dto para resposta de Informações Escolares.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class InformacoesAssentamentoResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String lotacaoId;
    private String lotacaoNome;
    private String lotacaoDescricao;
    private String orgaogovId;
    private String orgaogovNome;
    private String orgaogovDescricao;
    private String superintendenciaId;
    private String superintendenciaNome;
    private String superintendenciaDescricao;
    private String diretoriaId;
    private String diretoriaNome;
    private String diretoriaDescricao;
    private String atomovimentacaoId;
    private String atomovimentacaoNome;
    private String atomovimentacaoDescricao;
    private String atomovimentacaoVinculo;
    private String atomovimentacaoSimbolo;
    private String atomovimentacaoDiarioOficial;
    private String atomovimentacaoDataEfeito;
    private String matriculaInstitucional;
    private String EmailInstitucionalGov;
    private String servidorId;
    private String servidorNome;
    private boolean ativo;
}
