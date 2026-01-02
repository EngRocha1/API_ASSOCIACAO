package com.example.work3.v1.Afastamentos.dto;

import com.example.work3.v1.Afastamentos.domain.Afastamentos;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class AfastamentosResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private LocalDate DataReferencia;
    private LocalDate PeriodoInicio;
    private LocalDate PeriodoFim;
    private Afastamentos.Status statusTipo;
    private Afastamentos.StatusSolicitacao solicitacaoTipo;
    private Afastamentos.StatusRequerimento requerimentoTipo;
    private Afastamentos.StatusSIAPI sIAPITipo;
    private String justificativa;
    private String numeroSEI;
    private String servidorId;
    private String servidorNome;
    private CIDResponseDTO cid;
    private FluxoAprovacaoResponseDTO fluxoAprovacao;
    private SuspensaoResponseDTO suspensao;
    private TipoAfastamentoResponseDTO tipoAfastamento;
    private boolean ativo;
}
