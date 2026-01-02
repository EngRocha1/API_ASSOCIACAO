package com.example.work3.v1.SuperClasses.controller;
import com.example.work3.Exceptions.*;
import com.example.work3.v1.SuperClasses.dto.BaseDTO;
import com.example.work3.v1.SuperClasses.classes.Auditable;
import com.example.work3.v1.SuperClasses.interfaceOpenAPI.InterfaceOpenAPI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractController<T extends Auditable, DTO> implements InterfaceOpenAPI<DTO> {

    protected abstract List<T> getAllEntities();
    protected abstract T saveEntity(T entity);
    protected abstract T updateEntity(String id, T entity) throws Exception;
    protected abstract void deleteEntity(String id);
    protected abstract T findEntityById(String id);
    protected abstract T toEntity(DTO dto);
    protected abstract DTO toResponseDTO(T entity, UserDetails userDetails);

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    public ResponseEntity<List<DTO>> getAll() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
        List<T> allEntities = getAllEntities();
        List<DTO> responseDTOs = allEntities.stream()
                .map(entity -> toResponseDTO(entity, userDetails))
                .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    public ResponseEntity<DTO> update(@PathVariable String id, @RequestBody @Valid DTO data) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        T entity = toEntity(data);
        try {
            T updatedEntity = updateEntity(id, entity);
            DTO responseDTO = toResponseDTO(updatedEntity, userDetails);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        T existingEntity = findEntityById(id);
        if (!canEditEntity(userDetails, existingEntity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            deleteEntity(id);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DTO> getById(String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        T entity = findEntityById(id);
        DTO responseDTO = toResponseDTO(entity, userDetails);
        return ResponseEntity.ok(responseDTO);
    }

    protected boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    protected <D extends BaseDTO> D populateAdminFields(D dto, Auditable entity, UserDetails userDetails) {
        if (isAdmin(userDetails)) {

            dto.setCreatedBy(entity.getCreatedBy());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setModifiedBy(entity.getModifiedBy());
            dto.setModifiedDate(entity.getModifiedDate());

        }

        return dto;

    }

    protected boolean canEditEntity(UserDetails userDetails, T entity) {
        // Verificar se o usuário é um administrador
        if (isAdmin(userDetails)) {
            return true;
        }

        // Verificar se o usuário é o criador da entidade
        if (entity instanceof Auditable) {
            Auditable auditableEntity = (Auditable) entity;
            String createdBy = auditableEntity.getCreatedBy();
            if (createdBy != null && createdBy.equals(userDetails.getUsername())) {
                return true;
            }
        }

        return false;
    }

    protected void handleException(Exception e) {
        if (e instanceof NotFoundException ||
                e instanceof UnauthorizedAccessException ||
                e instanceof AlreadyExistsException) {
            ExceptionHandlerMap.getHandler(e.getClass()).accept(e);
        }
    else { ExceptionHandlers.handleUnexpectedException(e);
    }
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERINTENDENT', 'DIRECTOR', 'COORDINATOR', 'EMPLOYEE')")
    public ResponseEntity<DTO> register(@RequestBody @Valid DTO data) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User Details: " + userDetails);
        System.out.println("Authorities: " + userDetails.getAuthorities());
        T entity = toEntity(data);
        try {
            T savedEntity = saveEntityWithExceptionHandling(entity);
            DTO responseDTO = toResponseDTO(savedEntity, userDetails);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private T saveEntityWithExceptionHandling(T entity) {
        try {
            return saveEntity(entity);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

}
