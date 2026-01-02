package com.associacao.api.Exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ExceptionHandlerMap {
    private static final Map<Class<? extends Exception>, Consumer<Exception>> exceptionHandlers = new HashMap<>();

    static {
        // Mapeamento de exceções específicas
        exceptionHandlers.put(NotFoundException.class, e -> ExceptionHandlers.handleNotFoundException((NotFoundException) e));
        exceptionHandlers.put(UnauthorizedAccessException.class, e -> ExceptionHandlers.handleUnauthorizedAccessException((UnauthorizedAccessException) e));
        exceptionHandlers.put(AlreadyExistsException.class, e -> ExceptionHandlers.handleAlreadyExistsException((AlreadyExistsException) e));
        exceptionHandlers.put(BadRequestException.class, e -> ExceptionHandlers.handleBadRequestException((BadRequestException) e));
        exceptionHandlers.put(ForbiddenException.class, e -> ExceptionHandlers.handleForbiddenException((ForbiddenException) e));
        exceptionHandlers.put(ConflictException.class, e -> ExceptionHandlers.handleConflictException((ConflictException) e));
        exceptionHandlers.put(InternalServerErrorException.class, e -> ExceptionHandlers.handleInternalServerErrorException((InternalServerErrorException) e));
        exceptionHandlers.put(MethodNotAllowedException.class, e -> ExceptionHandlers.handleMethodNotAllowedException((MethodNotAllowedException) e));
        exceptionHandlers.put(UnsupportedMediaTypeException.class, e -> ExceptionHandlers.handleUnsupportedMediaTypeException((UnsupportedMediaTypeException) e));
        exceptionHandlers.put(ServiceUnavailableException.class, e -> ExceptionHandlers.handleServiceUnavailableException((ServiceUnavailableException) e));
        exceptionHandlers.put(GatewayTimeoutException.class, e -> ExceptionHandlers.handleGatewayTimeoutException((GatewayTimeoutException) e));
        exceptionHandlers.put(ResourceNotFoundException.class, e -> ExceptionHandlers.handleResourceNotFoundException((ResourceNotFoundException) e));
    }

    public static Consumer<Exception> getHandler(Class<? extends Exception> exceptionClass) {
        return exceptionHandlers.getOrDefault(exceptionClass, ExceptionHandlers::handleUnexpectedException);
    }
}

