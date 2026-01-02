package com.associacao.api.v1.InformacoesEscolares.dto;
import com.associacao.api.v1.InformacoesEscolares.domain.Listas.InstituicaoEnsino;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class InstituicaoEnsinoResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private String redesSociais;
    private String site;
    private Integer numeroProfessores;
    private Integer numeroAlunos;
    private LocalDate dataFundacao;
    private InstituicaoEnsino.NivelEnsino nivelEnsino;
    private InstituicaoEnsino.TipoInstituicao tipo;
    private String email;
    private String telefone;
    private String cnpj;
    private String endereco;
    private boolean ativo;
}

