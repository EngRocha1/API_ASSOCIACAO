package com.associacao.api.v1.InformacoesAssentamento.dto;

import com.associacao.api.v1.InformacoesAssentamento.domain.Listas.DiarioOficial;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class AtoMovimentacaoResponseDTO extends Auditable implements BaseDTO {
    private String id;

    @NotBlank
    private String name;
    private String NomeacaoExoneracao;
    private boolean ativo;
    private String simboloId;
    private String simboloNome;
    private String simboloDescricao;
    private String vinculoId;
    private String vinculoNome;
    private String vinculoDescricao;
    private String cargoId;
    private String cargoNome;
    private String cargoDescricao;
    private DiarioOficial diariooficial;
    private LocalDate DataEfeito;
}