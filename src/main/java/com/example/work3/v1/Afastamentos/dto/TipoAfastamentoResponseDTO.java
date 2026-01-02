package com.example.work3.v1.Afastamentos.dto;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class TipoAfastamentoResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private String descricao;
    private boolean ativo;
}