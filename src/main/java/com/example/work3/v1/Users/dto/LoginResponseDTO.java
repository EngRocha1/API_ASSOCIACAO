package com.example.work3.v1.Users.dto;
import com.example.work3.v1.Users.domain.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDTO{

    private String token;
    private UserRole role;
    private String Id;
}
