package com.example.work3.v1.Users.controller;
import com.example.work3.v1.SuperClasses.controller.AbstractController;
import com.example.work3.v1.Users.dto.AuthenticationDTO;
import com.example.work3.v1.Users.dto.LoginResponseDTO;
import com.example.work3.v1.Users.dto.RegisterDTO;
import com.example.work3.v1.Users.domain.Users;
import com.example.work3.v1.Users.service.UserService;
import com.example.work3.Security.TokenService;
import com.example.work3.v1.Servidor.repository.ServidorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


@RestController
@SQLDelete(sql = "UPDATE auth SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@RequestMapping("/auth")
@Tag(name = "Users", description = "Operações relacionadas ao gerenciamento e consulta dos usuários do sistema, incluindo criação, atualização, listagem e exclusão, com login e senha de acesso.")
public class UsersController extends AbstractController<Users, RegisterDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final ServidorRepository servidorRepository;

    @Autowired public UsersController(
            AuthenticationManager authenticationManager,
            UserService userService,
            TokenService tokenService,
            ServidorRepository servidorRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.servidorRepository = servidorRepository; }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        logger.info("Login attempt for user: {}", data.getLogin());
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
            logger.debug("UsernamePasswordAuthenticationToken: {}", usernamePassword);
            var auth = this.authenticationManager.authenticate(usernamePassword);
            logger.debug("Authentication result: {}", auth);
            var user = (Users) auth.getPrincipal();

            if (!user.getVerifyCode()) {
                // MFA desativado, gerar token diretamente
                String token = tokenService.generatedToken(user);
                logger.info("Login bem-sucedido para o usuário: {} (MFA desativado)", data.getLogin());
                return ResponseEntity.ok(new LoginResponseDTO(token, user.getRole(),user.getId()));
            }

            // MFA ativado, verificar código do Google Authenticator
            String secret = user.getSecret();
            int verificationCode = data.getVerificationCode();

            if (userService.verifyCode(user.getLogin(), secret, verificationCode)) {
                String token = tokenService.generatedToken(user);
                logger.info("Login bem-sucedido para o usuário: {} (MFA ativado)", data.getLogin());
                return ResponseEntity.ok(new LoginResponseDTO(token, user.getRole(),user.getId()));
            }
            else {
                logger.warn("Código de verificação inválido para o usuário: {}", data.getLogin());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de verificação inválido");
            }

        } catch (Exception e) {
            logger.error("Falha no login para o usuário: {}", data.getLogin(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no Login");
        }
    }


    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterDTO> register(@RequestBody @Valid RegisterDTO data) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.checkAuthorization(userDetails);

        Users user = toEntity(data);
        Users registeredUser = userService.register(user, userDetails);
        RegisterDTO responseDTO = toResponseDTO(registeredUser, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterDTO> update(@PathVariable String id, @RequestBody @Valid RegisterDTO data) throws Exception {
        Users user = toEntity(data);
        String qrCodeUrl = userService.updateAndGetQRUrl(id, user);
        Users updatedUser = userService.findById(id);
        RegisterDTO responseDTO = toResponseDTO(updatedUser, (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (qrCodeUrl != null) {
            responseDTO.setQrCodeUrl(qrCodeUrl); // Adiciona a URL do QR Code na resposta
        }
        return ResponseEntity.ok(responseDTO);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    protected List<Users> getAllEntities() {
        return userService.getAll();
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    protected Users saveEntity(@RequestBody @Valid Users entity) {
        Users registeredUser = userService.register(entity);
        String qrCodeUrl = userService.getQRBarcodeURL(registeredUser);
        logger.info("QR Code URL for user {}: {}", registeredUser.getLogin(), qrCodeUrl);
        return registeredUser;
    }



    @Override
    @PreAuthorize("hasRole('ADMIN')")
    protected Users updateEntity(@PathVariable String id, @RequestBody @Valid Users entity) throws Exception {
        return userService.update(id, entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    protected void deleteEntity(@PathVariable String id) {
        userService.delete(id);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    protected Users findEntityById(@PathVariable String id) {
        return userService.findById(id);
    }

    @RestController
    @RequestMapping("/protected")
    public static class ProtectedController {

        @GetMapping("/route")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<Object> getProtectedData() {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            return ResponseEntity.ok(response);
        }
    }


    @Override
    protected Users toEntity(RegisterDTO dto) {
        Users user = new Users(
                dto.getId(),
                dto.getLogin(),
                dto.getPassword(),
                dto.getRole(),
                dto.getAtivo(),
                dto.getVerifyCode()
        );
        return user;
    }

    @Override
    protected RegisterDTO toResponseDTO(Users entity, UserDetails userDetails) {
        RegisterDTO dto = RegisterDTO.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .role(entity.getRole())
                .ativo(entity.getAtivo())
                .verifyCode(entity.getVerifyCode())
                .build();
        if (Boolean.TRUE.equals(entity.getVerifyCode())) {
            dto.setQrCodeUrl(userService.getQRBarcodeURL(entity));
        }
        return dto;
    }

}
