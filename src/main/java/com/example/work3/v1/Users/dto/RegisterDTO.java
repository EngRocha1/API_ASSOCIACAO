package com.example.work3.v1.Users.dto;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.Users.domain.UserRole;
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
