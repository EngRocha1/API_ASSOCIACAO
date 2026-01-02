package com.example.work3.v1.Treinamentos.dto;

import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class TipoAprendizadoResponseDTO extends Auditable implements BaseDTO {
    private String id;

    @NotBlank
    private String name;
    private boolean ativo;
}