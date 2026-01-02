package com.example.work3.v1.InformacoesEscolares.dto;

import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class CursoResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private boolean ativo;
}