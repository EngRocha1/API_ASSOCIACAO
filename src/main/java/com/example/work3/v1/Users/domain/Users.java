package com.example.work3.v1.Users.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import com.example.work3.v1.SuperClasses.classes.Auditable;

@Table(name = "users")
@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLRestriction(value = "ativo = true")
public class Users extends Auditable implements UserDetails {
    @NotBlank(message = "Login não pode ser vazio")
    private String login;

    private String password;

    private UserRole role;

    private Boolean ativo;


    @Column(name = "verify_code", nullable = false)
    private Boolean verifyCode;

    public void setVerifyCode(Boolean verifyCode) {
        this.verifyCode = verifyCode;
        if (Boolean.FALSE.equals(verifyCode)) {
            this.secret = null; // Remove o segredo quando o MFA é desativado
        }
    }

    private String secret;

    public Users(String id, String login, String password, UserRole role, boolean ativo, Boolean verifyCode) {
        super.setId(null); // O ID está sendo configurado como null para gerar um novo UUID
        this.login = login;
        this.password = password;
        this.role = role;
        this.ativo = ativo;
        this.verifyCode = verifyCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        switch (this.role) {
            case ADMIN:
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case SUPERINTENDENT:
                authorities.add(new SimpleGrantedAuthority("ROLE_SUPERINTENDENT"));
                break;
            case DIRECTOR:
                authorities.add(new SimpleGrantedAuthority("ROLE_DIRECTOR"));
                break;
            case COORDINATOR:
                authorities.add(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
                break;
            case EMPLOYEE:
                authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
                break;
            case INTERN:
                authorities.add(new SimpleGrantedAuthority("ROLE_INTERN"));
                break;
            default:
                break;
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }

    public boolean canEditEntity(Users targetUser) {
        switch (this.role) {
            case ADMIN:
                return true;
            case SUPERINTENDENT:
                return targetUser.getRole() != UserRole.ADMIN && targetUser.getRole() != UserRole.SUPERINTENDENT;
            case DIRECTOR:
                return targetUser.getRole() != UserRole.ADMIN && targetUser.getRole() != UserRole.SUPERINTENDENT && targetUser.getRole() != UserRole.DIRECTOR;
            case COORDINATOR:
            case EMPLOYEE:
            case INTERN:
                return this.getId().equals(targetUser.getId());
            default:
                return false;
        }
    }


}
