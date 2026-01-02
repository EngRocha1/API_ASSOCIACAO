package com.associacao.api.v1.InformacoesSensiveis.dto;

import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstadoCivilResponseDTO  extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private String descricao;
    private boolean ativo;
}

