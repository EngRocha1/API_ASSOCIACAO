package com.associacao.api.v1.Users.dto;
import com.associacao.api.v1.SuperClasses.dto.BaseDTO;
import com.associacao.api.v1.SuperClasses.classes.Auditable;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class AuthenticationDTO extends Auditable implements BaseDTO {

    private String login;
    private String password;
    private int verificationCode;

}
