package com.associacao.api.v1.InformacoesEscolares.dto;

import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import lombok.*;

import java.time.LocalDate;

/**
 * dto para resposta de Informações Escolares.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class InformacoesEscolaresResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String cursoId;
    private String cursoNome;
    private String cursoDescricao;
    private LocalDate inicioCurso;
    private Integer cargaHorariaAtual;
    private Integer cargaHorariaTotal;
    private String formacaoAcademicaId;
    private String formacaoAcademicaNome;
    private String formacaoAcademicaDescricao;
    private String statusFormacaoId;
    private String statusFormacaoNome;
    private String statusFormacaoDescricao;
    private String instituicaoEnsinoId;
    private String instituicaoEnsinoNome;
    private String instituicaoEnsinoDescricao;
    private String matriculaInstitucional;
    private String periodoDoDiaId;
    private String periodoDoDiaNome;
    private String periodoDoDiaDescricao;
    private String semestreId;
    private String semestreNome;
    private String semestreDescricao;
    private String servidorId;
    private String servidorNome;
    private LocalDate terminoPrevisto;
    private boolean ativo;
}
