package com.associacao.api.v1.Users.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    SUPERINTENDENT("superintendent"),
    DIRECTOR("director"),
    COORDINATOR("coordinator"),
    EMPLOYEE("employee"),
    INTERN("intern"),
    GESTOR_ESTAGIOS("gestor_estagios"),
    GESTOR_TERCEIROS("gestor_terceiros"),
    GESTOR_TREINAMENTOS("gestor_treinamentos"),
    GESTOR_PSICOLOGOS("gestor_psicologos");

    private final String role;

    public static List<UserRole> getAllRoles() {
        return List.of(UserRole.values());
    }

}
