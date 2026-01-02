package com.example.work3.v1.Users.dto;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import com.example.work3.v1.SuperClasses.classes.Auditable;
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
