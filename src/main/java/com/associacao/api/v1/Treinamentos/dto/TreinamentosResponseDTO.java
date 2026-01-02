package com.associacao.api.v1.Treinamentos.dto;

import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para resposta de Treinamentos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TreinamentosResponseDTO extends Auditable implements BaseDTO {

    private String id;
    private String tipoAprendizado;
    private List<TreinamentoCursoResponseDTO> cursos;
    private String cpf;
    private String email;
    private String telefone;
    private String instituicaoResponsavel;
    private String numeroDocSei;
    private String genero;
    private LocalDate dataNascimento;
    private String servidorId;
    private String servidorNome;
    private boolean ativo;
}