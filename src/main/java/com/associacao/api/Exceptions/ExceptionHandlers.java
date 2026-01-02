package com.associacao.api.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class ExceptionHandlers {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    public static void handleNotFoundException(NotFoundException e) {
        logger.error("Erro ao realizar a operação", e);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A opção escolhida não foi encontrada.");
    }

    public static void handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        logger.error("Erro ao realizar a operação", e);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para realizar esta operação.");
    }

    public static void handleAlreadyExistsException(AlreadyExistsException e) {
        logger.error("Erro ao registrar a informação", e);
        throw new ResponseStatusException(HttpStatus.CONFLICT, "A informação já existe.");
    }

    public static void handleBadRequestException(BadRequestException e) {
        logger.error("Erro ao processar a requisição", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requisição inválida.");
    }

    public static void handleForbiddenException(ForbiddenException e) {
        logger.error("Acesso proibido", e);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso proibido.");
    }

    public static void handleConflictException(ConflictException e) {
        logger.error("Conflito ao processar a requisição", e);
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito ao processar a requisição.");
    }

    public static void handleInternalServerErrorException(InternalServerErrorException e) {
        logger.error("Erro interno do servidor", e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor.");
    }

    public static void handleMethodNotAllowedException(MethodNotAllowedException e) {
        logger.error("Método não permitido", e);
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Método não permitido.");
    }

    public static void handleUnsupportedMediaTypeException(UnsupportedMediaTypeException e) {
        logger.error("Tipo de mídia não suportado", e);
        throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Tipo de mídia não suportado.");
    }

    public static void handleServiceUnavailableException(ServiceUnavailableException e) {
        logger.error("Serviço indisponível", e);
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Serviço indisponível.");
    }

    public static void handleGatewayTimeoutException(GatewayTimeoutException e) {
        logger.error("Tempo limite do gateway excedido", e);
        throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Tempo limite do gateway excedido.");
    }

    public static void handleUnexpectedException(Exception e) {
        logger.error("Erro inesperado ao realizar a operação", e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao realizar a operação.");
    }

    // Novo método para ResourceNotFoundException
    public static void handleResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("Recurso não encontrado", e);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado.");
    }
}
