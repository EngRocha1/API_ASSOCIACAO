package com.associacao.api.v1.Users.dto;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import com.associacao.api.v1.Users.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterDTO extends Auditable implements BaseDTO {
    private String id;
    private String login;
    private String password;
    private UserRole role;
    private Boolean ativo;
    private Boolean verifyCode;
    private String qrCodeUrl;
}
