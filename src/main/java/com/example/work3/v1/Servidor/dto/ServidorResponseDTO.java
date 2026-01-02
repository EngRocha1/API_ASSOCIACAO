package com.example.work3.v1.Servidor.dto;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServidorResponseDTO extends Auditable implements BaseDTO {
    private String id;
    private String name;
    private boolean ativo;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
    private LocalDate dataNascimento;

    private String usuarioId;
    private String usuarioNome;

    // Campos para upload e download de arquivos
    private String filename;
    private String fileDownloadUri;
}
