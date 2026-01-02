package com.example.work3.v1.Users.dto;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.Users.domain.UserRole;
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
