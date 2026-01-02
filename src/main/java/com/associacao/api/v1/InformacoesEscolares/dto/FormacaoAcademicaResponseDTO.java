package com.associacao.api.v1.InformacoesEscolares.dto;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class FormacaoAcademicaResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private boolean ativo;
}

