package com.associacao.api.v1.Afastamentos.dto;

import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class SuspensaoResponseDTO extends Auditable implements BaseDTO {
    private String id;

    @NotBlank
    private String name;
    private String descricao;
    private boolean ativo;
    private String afastamentosId;
    private String afastamentosNome;
    private String afastamentosPeriodoInicial;
    private String afastamentosPeriodoFinal;

}