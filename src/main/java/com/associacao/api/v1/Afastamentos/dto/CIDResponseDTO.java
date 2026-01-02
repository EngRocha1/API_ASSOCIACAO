package com.associacao.api.v1.Afastamentos.dto;

import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class CIDResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private String descricao;
    private boolean ativo;
    private String codigo;

}