package com.associacao.api.v1.Users.dto;
import com.associacao.api.v1.Users.domain.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDTO{

    private String token;
    private UserRole role;
    private String Id;
}
