package com.associacao.api.v1.Afastamentos.dto;

import com.associacao.api.v1.Afastamentos.domain.Listas.FluxoAprovacao;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class FluxoAprovacaoResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private String descricao;
    private FluxoAprovacao.StatusAprovacao AprovacaoTipo;
    private LocalDate DataAprovacao;
    private LocalDate DataSolicitacao;
    private String AprovadoPor;
    private String SolicitadoPor;
    private boolean ativo;
}