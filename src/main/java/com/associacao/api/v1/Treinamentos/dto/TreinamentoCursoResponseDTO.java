package com.associacao.api.v1.Treinamentos.dto;

import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TreinamentoCursoResponseDTO extends Auditable implements BaseDTO {

    private String id;

    @NotBlank
    private String name;
    private boolean ativo;
    private String tipoCurso;
    private String turmaNumero;
    private String instrutor;
    private String conteudoCurso;
    private Integer cargaHoraria;
}
