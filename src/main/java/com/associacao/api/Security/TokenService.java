package com.associacao.api.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.associacao.api.v1.Users.domain.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Serviço responsável pela geração e validação de tokens JWT.
 */
@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário fornecido.
     *
     * @param user O usuário para o qual o token será gerado.
     * @return O token JWT gerado.
     * @throws RuntimeException Se ocorrer um erro durante a geração do token.
     */
    public String generatedToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (Exception exception) {
            logger.error("Error generating token: {}", exception.getMessage());
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o subject (assunto) do token.
     *
     * @param token O token JWT a ser validado.
     * @return O subject do token, ou null se a validação falhar.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            logger.error("Token validation failed: {}", exception.getMessage());
            return null;
        }
    }

    /**
     * Gera a data de expiração para o token JWT (1 hora a partir do momento atual).
     *
     * @return A data de expiração do token.
     */
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}