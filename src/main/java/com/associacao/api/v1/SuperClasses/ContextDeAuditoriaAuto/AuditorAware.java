package com.associacao.api.v1.SuperClasses.ContextDeAuditoriaAuto;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAware implements org.springframework.data.domain.AuditorAware<String> {
    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        String auditor;
        if (principal instanceof UserDetails) {
            auditor = ((UserDetails) principal).getUsername();
        } else {
            auditor = principal.toString();
        }

        System.out.println("Current Auditor: " + auditor); // Adicione este log para depuração
        return Optional.of(auditor);
    }
}
