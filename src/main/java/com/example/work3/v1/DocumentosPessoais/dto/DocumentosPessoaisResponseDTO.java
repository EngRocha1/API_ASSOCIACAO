package com.example.work3.v1.DocumentosPessoais.dto;

import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO para resposta de Informações de Documentos Pessoais.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DocumentosPessoaisResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String tipoDeDocumentoId;
    private String tipoDeDocumentoNome;
    private String tipoDeDocumentoDescricao;
    private String numeroRg;
    private String naturalidade;
    private String nacionalidade;
    private LocalDate validadeRg;
    private String filiacaoPaiRg;
    private String filiacaoMaeRg;
    private LocalDate dataEmissaoRg;
    private String orgaoExpedidorRg;
    private String numeroCpf;
    private String situacaoCadastralCpf;
    private LocalDate dataInscricaoCpf;
    private String digitoVerificadorCpf;
    private LocalDate dataEmissaoCpf;
    private String numeroTituloEleitor;
    private LocalDate dataEmissaoTitulo;
    private String zonaTitulo;
    private String secaoTitulo;
    private String numeroCtps;
    private LocalDate dataEmissaoCtps;
    private String orgaoExpedidorCtps;
    private String numeroPisNis;
    private String numeroReservista;
    private LocalDate dataEmissaoReservista;
    private String regiaoMilitarReservista;
    private boolean ativo;
    private String servidorId;
    private String servidorNome;
}