package com.associacao.api.v1.InformacoesSensiveis.dto;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class CorPeleResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private String descricao;
    private boolean ativo;
}