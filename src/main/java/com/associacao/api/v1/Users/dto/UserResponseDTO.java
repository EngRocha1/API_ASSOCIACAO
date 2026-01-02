package com.associacao.api.v1.Users.dto;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.Users.domain.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class UserResponseDTO extends Auditable implements BaseDTO {
    private String id;
    @NotBlank
    private String login;
    private UserRole role;
    private Boolean ativo;


}
