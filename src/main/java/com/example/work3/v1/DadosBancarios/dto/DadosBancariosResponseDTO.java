package com.example.work3.v1.DadosBancarios.dto;
import com.example.work3.v1.DadosBancarios.domain.DadosBancarios;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import lombok.*;

/**
 * dto para resposta de Informações Escolares.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class DadosBancariosResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String BancoId;
    private String BancoNome;
    private String BancoDescricao;
    private String Conta;
    private String Agencia;
    private String ChavePix;
    private DadosBancarios.TipoChave tipo;
    private String servidorId;
    private String servidorNome;
    private boolean ativo;
}
